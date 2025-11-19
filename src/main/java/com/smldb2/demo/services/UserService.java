package com.smldb2.demo.services;

import com.smldb2.demo.DTO.UserDetailedStatsDTO;
import com.smldb2.demo.Entity.Rib;
import com.smldb2.demo.Entity.Tel;
import com.smldb2.demo.Entity.User;
import com.smldb2.demo.DTO.UserStatsDTO;
import com.smldb2.demo.repositories.RibRepository;
import com.smldb2.demo.repositories.TelRepository;
import com.smldb2.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RibRepository ribRepository;

    @Autowired
    private TelRepository telRepository;



    public Optional<User> getUserByName(String name) {
        return userRepository.findByPersoName(name);
    }

    public List<User> getUsersByEmail(String email) {
        return userRepository.findByEmailContaining(email);
    }

    public List<User> getUsersBySexe(String sexe) {
        return userRepository.findBySexe(sexe);
    }

    private String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02X", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> login(String persoId, String password) {
        String hashedPassword = md5(password);
        return userRepository.findByPersoIdAndPersoPassed(persoId, hashedPassword);
    }

    private int calculateAge(Date birthDate) {
        LocalDate birth = birthDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate now = LocalDate.now();
        return Period.between(birth, now).getYears();
    }

    // ========== MÉTHODES POUR RÉCUPÉRER LES ENTREPRISES ==========

    public List<String> getAllCompanyCodes() {
        return userRepository.findAllDistinctCompanyCodes();
    }

    // ========== STATISTIQUES GLOBALES ==========

    public UserStatsDTO getGlobalStats() {
        System.out.println("📊 Début de getGlobalStats (toutes entreprises)");

        long total = userRepository.count();
        System.out.println("✅ Total adhérents: " + total);

        long nouveaux = 0;

        try {
            LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
            Date startOfMonth = Date.from(firstDayOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());

            nouveaux = userRepository.countByDateCreationAfter(startOfMonth);
            System.out.println("✅ Nouveaux adhérents ce mois: " + nouveaux);

        } catch (Exception e) {
            System.err.println("⚠️ Impossible de calculer les nouveaux adhérents: " + e.getMessage());
            nouveaux = 0;
        }

        UserStatsDTO stats = new UserStatsDTO(total, nouveaux);
        return stats;
    }

    public UserDetailedStatsDTO getDetailedStats() {
        System.out.println("📊 Début de getDetailedStats (toutes entreprises)");

        List<User> allUsers = userRepository.findAll();
        return calculateDetailedStats(allUsers);
    }

    public Map<String, Long> getMonthlyEvolution() {
        List<User> allUsers = userRepository.findAll();
        return calculateMonthlyEvolution(allUsers);
    }

    // ========== STATISTIQUES PAR ENTREPRISE ==========

    public UserStatsDTO getGlobalStatsByCompany(String codeEntreprise) {
        System.out.println("📊 Début de getGlobalStatsByCompany pour: " + codeEntreprise);

        long total = userRepository.countByCodeEntreprise(codeEntreprise);
        System.out.println("✅ Total adhérents entreprise " + codeEntreprise + ": " + total);

        long nouveaux = 0;

        try {
            LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
            Date startOfMonth = Date.from(firstDayOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());

            nouveaux = userRepository.countByCodeEntrepriseAndDateCreationAfter(codeEntreprise, startOfMonth);
            System.out.println("✅ Nouveaux adhérents entreprise " + codeEntreprise + " ce mois: " + nouveaux);

        } catch (Exception e) {
            System.err.println("⚠️ Impossible de calculer les nouveaux adhérents: " + e.getMessage());
            nouveaux = 0;
        }

        UserStatsDTO stats = new UserStatsDTO(total, nouveaux);
        return stats;
    }

    public UserDetailedStatsDTO getDetailedStatsByCompany(String codeEntreprise) {
        System.out.println("📊 Début de getDetailedStatsByCompany pour: " + codeEntreprise);

        List<User> companyUsers = userRepository.findByCodeEntreprise(codeEntreprise);
        System.out.println("✅ Nombre d'utilisateurs de l'entreprise " + codeEntreprise + ": " + companyUsers.size());

        return calculateDetailedStats(companyUsers);
    }

    public Map<String, Long> getMonthlyEvolutionByCompany(String codeEntreprise) {
        List<User> companyUsers = userRepository.findByCodeEntreprise(codeEntreprise);
        return calculateMonthlyEvolution(companyUsers);
    }

    // ========== MÉTHODES PRIVÉES COMMUNES ==========

    private UserDetailedStatsDTO calculateDetailedStats(List<User> users) {
        Map<String, Long> repartitionParSexe = new HashMap<>();
        repartitionParSexe.put("M", 0L);
        repartitionParSexe.put("F", 0L);
        repartitionParSexe.put("AUTRE", 0L);

        Map<String, Long> repartitionParSituationFamiliale = new HashMap<>();

        for (User user : users) {
            // Compter par sexe
            String sexe = user.getSexe();
            if (sexe != null && !sexe.isEmpty()) {
                if (sexe.equalsIgnoreCase("M") || sexe.equalsIgnoreCase("HOMME") || sexe.equalsIgnoreCase("H")) {
                    repartitionParSexe.put("M", repartitionParSexe.get("M") + 1);
                } else if (sexe.equalsIgnoreCase("F") || sexe.equalsIgnoreCase("FEMME")) {
                    repartitionParSexe.put("F", repartitionParSexe.get("F") + 1);
                } else {
                    repartitionParSexe.put("AUTRE", repartitionParSexe.get("AUTRE") + 1);
                }
            } else {
                repartitionParSexe.put("AUTRE", repartitionParSexe.get("AUTRE") + 1);
            }

            // Compter par situation familiale
            String situation = user.getSituationFamiliale();
            if (situation != null && !situation.isEmpty()) {
                String situationUpper = situation.toUpperCase().trim();
                repartitionParSituationFamiliale.put(
                        situationUpper,
                        repartitionParSituationFamiliale.getOrDefault(situationUpper, 0L) + 1
                );
            } else {
                repartitionParSituationFamiliale.put(
                        "NON_DEFINI",
                        repartitionParSituationFamiliale.getOrDefault("NON_DEFINI", 0L) + 1
                );
            }
        }

        if (repartitionParSexe.get("AUTRE") == 0) {
            repartitionParSexe.remove("AUTRE");
        }

        System.out.println("📊 Répartition par sexe: " + repartitionParSexe);
        System.out.println("📊 Répartition par situation: " + repartitionParSituationFamiliale);

        UserDetailedStatsDTO stats = new UserDetailedStatsDTO();
        stats.setRepartitionParSexe(repartitionParSexe);
        stats.setRepartitionParSituationFamiliale(repartitionParSituationFamiliale);

        return stats;
    }

    private Map<String, Long> calculateMonthlyEvolution(List<User> users) {
        Map<String, Long> monthlyCount = new TreeMap<>();

        LocalDate now = LocalDate.now();
        for (int i = 11; i >= 0; i--) {
            LocalDate month = now.minusMonths(i);
            String monthKey = month.format(DateTimeFormatter.ofPattern("MMM yyyy", Locale.FRENCH));

            LocalDate startOfMonth = month.withDayOfMonth(1);
            LocalDate endOfMonth = month.withDayOfMonth(month.lengthOfMonth());

            Date startDate = Date.from(startOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(endOfMonth.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());

            long count = users.stream()
                    .filter(u -> u.getDateCreation() != null)
                    .filter(u -> !u.getDateCreation().before(startDate) && !u.getDateCreation().after(endDate))
                    .count();

            monthlyCount.put(monthKey, count);
        }

        return monthlyCount;
    }

    // ========== GESTION DU RIB - LOGIQUE CORRIGÉE ==========

    /**
     * Récupérer le RIB affiché pour l'utilisateur
     * - Si demande non traitée : affiche le nouveau RIB avec statut "en attente"
     * - Sinon : affiche le RIB actuel de la table users
     */
    public Map<String, Object> getRibByPersoId(String persoId) {
        try {
            Map<String, Object> response = new HashMap<>();

            // 1. Récupérer l'utilisateur
            Optional<User> userOpt = userRepository.findByPersoId(persoId);
            if (!userOpt.isPresent()) {
                throw new RuntimeException("Utilisateur non trouvé");
            }

            User user = userOpt.get();
            String ribActuel = user.getRib();

            // 2. Vérifier s'il existe une demande en attente (EXPORTED = "N")
            List<Rib> ribsEnAttente = ribRepository.findByPersoIdAndExported(persoId, "N");

            if (!ribsEnAttente.isEmpty()) {
                // Il y a une demande en attente
                Rib ribEnAttente = ribsEnAttente.get(0);

                response.put("persoId", persoId);
                response.put("rib", ribEnAttente.getNouveauRib());
                response.put("ancienRib", ribEnAttente.getAncienRib());
                response.put("enAttente", true);

                // Calculer le message selon l'heure de création et l'heure actuelle
                LocalTime now = LocalTime.now();
                LocalTime cutoffTime = LocalTime.of(11, 10);

                LocalDateTime creationDateTime = ribEnAttente.getDateCreation()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();

                LocalTime creationTime = creationDateTime.toLocalTime();
                LocalDate creationDate = creationDateTime.toLocalDate();
                LocalDate today = LocalDate.now();

                String message;
                if (creationDate.equals(today) && creationTime.isBefore(cutoffTime) && now.isBefore(cutoffTime)) {
                    message = "Votre RIB sera modifié aujourd'hui à 11h10";
                } else {
                    message = "Votre RIB sera modifié demain à 11h10";
                }

                response.put("message", message);

                System.out.println("📋 RIB en attente trouvé pour " + persoId);
                System.out.println("   Ancien: " + ribEnAttente.getAncienRib());
                System.out.println("   Nouveau: " + ribEnAttente.getNouveauRib());
                System.out.println("   Créé à: " + creationTime);
            } else {
                // Pas de demande en attente, retourner le RIB actuel
                response.put("persoId", persoId);
                response.put("rib", ribActuel != null ? ribActuel : "");
                response.put("enAttente", false);

                System.out.println("📋 RIB actif retourné pour " + persoId + ": " + ribActuel);
            }

            return response;

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération du RIB pour " + persoId + ": " + e.getMessage());
            throw new RuntimeException("Erreur lors de la récupération du RIB", e);
        }
    }

    /**
     * Demander un changement de RIB
     * Crée une entrée avec EXPORTED = "N"
     * L'utilisateur doit ensuite valider (mettre EXPORTED = "Y") avant 10h00
     */
    @Transactional
    public Map<String, Object> updateRib(String persoId, String newRib) {
        try {
            Map<String, Object> response = new HashMap<>();

            // 1. Vérifier que l'utilisateur existe
            Optional<User> userOpt = userRepository.findByPersoId(persoId);
            if (!userOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Utilisateur non trouvé");
                return response;
            }

            User user = userOpt.get();
            String ancienRib = user.getRib();

            // 2. Vérifier que le nouveau RIB est différent de l'ancien
            if (newRib.equals(ancienRib)) {
                response.put("success", false);
                response.put("message", "Le nouveau RIB est identique à l'ancien");
                return response;
            }

            // 3. Vérifier s'il existe déjà une demande en attente
            List<Rib> demandesExistantes = ribRepository.findByPersoIdAndExported(persoId, "N");
            if (!demandesExistantes.isEmpty()) {
                response.put("success", false);
                response.put("message", "Vous avez déjà une demande de changement de RIB en cours");
                return response;
            }

            // 4. Créer la demande avec EXPORTED = "N"
            Rib nouvelleDemandeRib = new Rib();
            nouvelleDemandeRib.setPersoId(persoId);
            nouvelleDemandeRib.setAncienRib(ancienRib);
            nouvelleDemandeRib.setNouveauRib(newRib);
            nouvelleDemandeRib.setExported("N"); // Par défaut
            nouvelleDemandeRib.setDateCreation(new Date());

            ribRepository.save(nouvelleDemandeRib);

            // 5. Message selon l'heure
            LocalTime now = LocalTime.now();
            LocalTime cutoffTime = LocalTime.of(10, 0);

            String message;
            if (now.isBefore(cutoffTime)) {
                message = "Votre demande sera traitée aujourd'hui à 11h10 (pensez à valider avant 11h10)";
            } else {
                message = "Votre demande sera traitée demain à 11h10 (pensez à valider avant 11h10)";
            }

            response.put("success", true);
            response.put("message", message);
            response.put("persoId", persoId);
            response.put("rib", newRib);
            response.put("enAttente", true);

            System.out.println("✅ Demande RIB créée - EXPORTED='N'");
            System.out.println("   PersoId: " + persoId);
            System.out.println("   Heure: " + now);

            return response;

        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Erreur lors de la demande");
            return errorResponse;
        }
    }

    /**
     * ✅ LOGIQUE FINALE CORRIGÉE
     *
     * Traiter les demandes de RIB UNIQUEMENT si TOUTES ces conditions sont remplies :
     * 1. Il est 10h00 ou après (appelé par le scheduler)
     * 2. EXPORTED = "Y" (demande validée)
     * 3. La demande a été créée AVANT 10h00 le jour même
     *
     * Exemples :
     * - 11h00 → Changement → EXPORTED='Y' → ❌ Attend demain 10h00
     * - 09h30 → Changement → EXPORTED='N' à 10h03 → ❌ Pas traité (N)
     * - 09h50 → Changement → EXPORTED='Y' à 10h00 → ✅ Traité
     */
    @Transactional
    public void traiterDemandesRibEnAttente() {
        try {
            System.out.println("========================================");
            System.out.println("🔄 TRAITEMENT AUTOMATIQUE DES RIBs");
            System.out.println("🕙 Heure actuelle: " + LocalTime.now());
            System.out.println("========================================");

            LocalTime cutoffTime = LocalTime.of(11, 10);
            LocalDateTime now = LocalDateTime.now();
            LocalDate today = now.toLocalDate();

            // ✅ Récupérer UNIQUEMENT les demandes avec EXPORTED = "Y"
            List<Rib> demandesValidees = ribRepository.findByExported("Y");

            System.out.println("📋 Demandes trouvées avec EXPORTED='Y': " + demandesValidees.size());

            if (demandesValidees.isEmpty()) {
                System.out.println("ℹ️ Aucune demande validée (EXPORTED='Y')");
                return;
            }

            int compteurTraitees = 0;
            int compteurIgnorees = 0;
            int compteurEchecs = 0;

            for (Rib demande : demandesValidees) {
                try {
                    System.out.println("----------------------------------------");
                    System.out.println("   📦 Analyse demande #" + demande.getNumRib());
                    System.out.println("   👤 PersoId: " + demande.getPersoId());
                    System.out.println("   🟢 EXPORTED: " + demande.getExported());

                    // Convertir la date de création
                    LocalDateTime creationDateTime = demande.getDateCreation()
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();

                    LocalTime creationTime = creationDateTime.toLocalTime();
                    LocalDate creationDate = creationDateTime.toLocalDate();

                    System.out.println("   📅 Date création: " + creationDate);
                    System.out.println("   🕐 Heure création: " + creationTime);

                    // ✅ VÉRIFICATION DES 3 CONDITIONS

                    // Condition 1 : Heure actuelle >= 11h10 (déjà vérifiée par le scheduler)

                    // Condition 2 : EXPORTED = "Y" (déjà filtrée dans la requête)

                    // Condition 3 : Demande créée AVANT 11h10 le jour même
                    boolean creeAvant11h10 = creationDate.equals(today) && creationTime.isBefore(cutoffTime);

                    if (!creeAvant11h10) {
                        System.out.println("   ⏭️ IGNORÉ - Demande créée après 11h10 ou pas aujourd'hui");
                        System.out.println("   ℹ️ Sera traitée demain à 11h10");
                        compteurIgnorees++;
                        continue;
                    }

                    // ✅ Toutes les conditions sont remplies, traiter la demande
                    System.out.println("   ✅ CONDITIONS VALIDÉES - Traitement en cours");

                    Optional<User> userOpt = userRepository.findByPersoId(demande.getPersoId());

                    if (userOpt.isPresent()) {
                        User user = userOpt.get();

                        System.out.println("   📌 RIB actuel: " + user.getRib());
                        System.out.println("   ✨ Nouveau RIB: " + demande.getNouveauRib());

                        // Mettre à jour le RIB dans users
                        user.setRib(demande.getNouveauRib());
                        userRepository.save(user);
                        System.out.println("   ✅ RIB mis à jour dans users");

                        // Marquer comme traité en changeant EXPORTED
                        demande.setExported("PROCESSED"); // Ou supprimer la demande
                        ribRepository.save(demande);
                        System.out.println("   ✅ Demande marquée comme traitée");

                        compteurTraitees++;

                    } else {
                        System.err.println("   ⚠️ Utilisateur non trouvé: " + demande.getPersoId());
                        compteurEchecs++;
                    }

                } catch (Exception e) {
                    System.err.println("   ❌ Erreur: " + e.getMessage());
                    e.printStackTrace();
                    compteurEchecs++;
                }
            }

            System.out.println("========================================");
            System.out.println("✅ TRAITEMENT TERMINÉ");
            System.out.println("   ✔️ Traitées: " + compteurTraitees);
            System.out.println("   ⏭️ Ignorées (après 11h10): " + compteurIgnorees);
            System.out.println("   ❌ Échecs: " + compteurEchecs);
            System.out.println("   📊 Total analysées: " + demandesValidees.size());
            System.out.println("========================================");

        } catch (Exception e) {
            System.err.println("❌ ERREUR GLOBALE: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ========== AUTRES MÉTHODES (inchangées) ==========

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public String getContactByPersoId(String persoId) {
        try {
            return userRepository.findContactByPersoId(persoId);
        } catch (Exception e) {
            System.err.println("❌ Erreur contact: " + e.getMessage());
            return null;
        }
    }

    public boolean updateContact(String persoId, String newContact) {
        try {
            Optional<User> userOpt = userRepository.findByPersoId(persoId);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setContact(newContact);
                userRepository.save(user);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            return false;
        }
    }
    public List<User> getUsersByCompany(String codeEntreprise) {
        try {
            return userRepository.findByCodeEntreprise(codeEntreprise);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération des utilisateurs de l'entreprise " + codeEntreprise + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }


    // ========== GESTION DU CONTACT/TÉLÉPHONE - LOGIQUE CORRIGÉE ==========

    /**
     * Récupérer le téléphone affiché pour l'utilisateur
     * - Si demande non traitée : affiche le nouveau tél avec statut "en attente"
     * - Sinon : affiche le téléphone actuel de la table users
     */
    public Map<String, Object> getContactByPersoIdV2(String persoId) {
        try {
            Map<String, Object> response = new HashMap<>();

            // 1. Récupérer l'utilisateur
            Optional<User> userOpt = userRepository.findByPersoId(persoId);
            if (!userOpt.isPresent()) {
                throw new RuntimeException("Utilisateur non trouvé");
            }

            User user = userOpt.get();
            String contactActuel = user.getContact();

            // 2. Vérifier s'il existe une demande en attente (EXPORTED = "N")
            List<Tel> telsEnAttente = telRepository.findByPersoIdAndExported(persoId, "N");

            if (!telsEnAttente.isEmpty()) {
                // Il y a une demande en attente
                Tel telEnAttente = telsEnAttente.get(0);

                response.put("persoId", persoId);
                response.put("contact", telEnAttente.getNouveauTel());
                response.put("ancienContact", telEnAttente.getAncienTel());
                response.put("enAttente", true);

                // Calculer le message selon l'heure de création et l'heure actuelle
                LocalTime now = LocalTime.now();
                LocalTime cutoffTime = LocalTime.of(11, 10);

                LocalDateTime creationDateTime = telEnAttente.getDateCreation()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();

                LocalTime creationTime = creationDateTime.toLocalTime();
                LocalDate creationDate = creationDateTime.toLocalDate();
                LocalDate today = LocalDate.now();

                String message;
                if (creationDate.equals(today) && creationTime.isBefore(cutoffTime) && now.isBefore(cutoffTime)) {
                    message = "Votre téléphone sera modifié aujourd'hui à 11h10";
                } else {
                    message = "Votre téléphone sera modifié demain à 11h10";
                }

                response.put("message", message);

                System.out.println("📱 Téléphone en attente trouvé pour " + persoId);
                System.out.println("   Ancien: " + telEnAttente.getAncienTel());
                System.out.println("   Nouveau: " + telEnAttente.getNouveauTel());
                System.out.println("   Créé à: " + creationTime);
            } else {
                // Pas de demande en attente, retourner le contact actuel
                response.put("persoId", persoId);
                response.put("contact", contactActuel != null ? contactActuel : "");
                response.put("enAttente", false);

                System.out.println("📱 Contact actif retourné pour " + persoId + ": " + contactActuel);
            }

            return response;

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération du contact pour " + persoId + ": " + e.getMessage());
            throw new RuntimeException("Erreur lors de la récupération du contact", e);
        }
    }

    /**
     * Demander un changement de téléphone
     * Crée une entrée avec EXPORTED = "N"
     * L'utilisateur doit ensuite valider (mettre EXPORTED = "Y") avant 11h10
     */
    @Transactional
    public Map<String, Object> updateContactV2(String persoId, String newContact) {
        try {
            Map<String, Object> response = new HashMap<>();

            // 1. Vérifier que l'utilisateur existe
            Optional<User> userOpt = userRepository.findByPersoId(persoId);
            if (!userOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Utilisateur non trouvé");
                return response;
            }

            User user = userOpt.get();
            String ancienContact = user.getContact();

            // 2. Vérifier que le nouveau contact est différent de l'ancien
            if (newContact.equals(ancienContact)) {
                response.put("success", false);
                response.put("message", "Le nouveau téléphone est identique à l'ancien");
                return response;
            }

            // 3. Vérifier s'il existe déjà une demande en attente
            List<Tel> demandesExistantes = telRepository.findByPersoIdAndExported(persoId, "N");
            if (!demandesExistantes.isEmpty()) {
                response.put("success", false);
                response.put("message", "Vous avez déjà une demande de changement de téléphone en cours");
                return response;
            }

            // 4. Créer la demande avec EXPORTED = "N"
            Tel nouvelleDemandeTel = new Tel();
            nouvelleDemandeTel.setPersoId(persoId);
            nouvelleDemandeTel.setAncienTel(ancienContact);
            nouvelleDemandeTel.setNouveauTel(newContact);
            nouvelleDemandeTel.setExported("N"); // Par défaut
            nouvelleDemandeTel.setDateCreation(new Date());

            telRepository.save(nouvelleDemandeTel);

            // 5. Message selon l'heure
            LocalTime now = LocalTime.now();
            LocalTime cutoffTime = LocalTime.of(11, 10);

            String message;
            if (now.isBefore(cutoffTime)) {
                message = "Votre demande sera traitée aujourd'hui à 11h10 (pensez à valider avant 11h10)";
            } else {
                message = "Votre demande sera traitée demain à 11h10 (pensez à valider avant 11h10)";
            }

            response.put("success", true);
            response.put("message", message);
            response.put("persoId", persoId);
            response.put("contact", newContact);
            response.put("enAttente", true);

            System.out.println("✅ Demande téléphone créée - EXPORTED='N'");
            System.out.println("   PersoId: " + persoId);
            System.out.println("   Heure: " + now);

            return response;

        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Erreur lors de la demande");
            return errorResponse;
        }
    }

    /**
     * ✅ LOGIQUE FINALE CORRIGÉE
     *
     * Traiter les demandes de téléphone UNIQUEMENT si TOUTES ces conditions sont remplies :
     * 1. Il est 11h10 ou après (appelé par le scheduler)
     * 2. EXPORTED = "Y" (demande validée)
     * 3. La demande a été créée AVANT 11h10 le jour même
     */
    @Transactional
    public void traiterDemandesTelEnAttente() {
        try {
            System.out.println("========================================");
            System.out.println("📱 TRAITEMENT AUTOMATIQUE DES TÉLÉPHONES");
            System.out.println("🕙 Heure actuelle: " + LocalTime.now());
            System.out.println("========================================");

            LocalTime cutoffTime = LocalTime.of(11, 10);
            LocalDateTime now = LocalDateTime.now();
            LocalDate today = now.toLocalDate();

            // ✅ Récupérer UNIQUEMENT les demandes avec EXPORTED = "Y"
            List<Tel> demandesValidees = telRepository.findByExported("Y");

            System.out.println("📋 Demandes trouvées avec EXPORTED='Y': " + demandesValidees.size());

            if (demandesValidees.isEmpty()) {
                System.out.println("ℹ️ Aucune demande validée (EXPORTED='Y')");
                return;
            }

            int compteurTraitees = 0;
            int compteurIgnorees = 0;
            int compteurEchecs = 0;

            for (Tel demande : demandesValidees) {
                try {
                    System.out.println("----------------------------------------");
                    System.out.println("   📦 Analyse demande #" + demande.getNumTel());
                    System.out.println("   👤 PersoId: " + demande.getPersoId());
                    System.out.println("   🟢 EXPORTED: " + demande.getExported());

                    // Convertir la date de création
                    LocalDateTime creationDateTime = demande.getDateCreation()
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();

                    LocalTime creationTime = creationDateTime.toLocalTime();
                    LocalDate creationDate = creationDateTime.toLocalDate();

                    System.out.println("   📅 Date création: " + creationDate);
                    System.out.println("   🕐 Heure création: " + creationTime);

                    // ✅ VÉRIFICATION DES 3 CONDITIONS

                    // Condition 1 : Heure actuelle >= 11h10 (déjà vérifiée par le scheduler)

                    // Condition 2 : EXPORTED = "Y" (déjà filtrée dans la requête)

                    // Condition 3 : Demande créée AVANT 11h10 le jour même
                    boolean creeAvant11h10 = creationDate.equals(today) && creationTime.isBefore(cutoffTime);

                    if (!creeAvant11h10) {
                        System.out.println("   ⏭️ IGNORÉ - Demande créée après 11h10 ou pas aujourd'hui");
                        System.out.println("   ℹ️ Sera traitée demain à 11h10");
                        compteurIgnorees++;
                        continue;
                    }

                    // ✅ Toutes les conditions sont remplies, traiter la demande
                    System.out.println("   ✅ CONDITIONS VALIDÉES - Traitement en cours");

                    Optional<User> userOpt = userRepository.findByPersoId(demande.getPersoId());

                    if (userOpt.isPresent()) {
                        User user = userOpt.get();

                        System.out.println("   📌 Contact actuel: " + user.getContact());
                        System.out.println("   ✨ Nouveau contact: " + demande.getNouveauTel());

                        // Mettre à jour le contact dans users
                        user.setContact(demande.getNouveauTel());
                        userRepository.save(user);
                        System.out.println("   ✅ Contact mis à jour dans users");

                        // Marquer comme traité en changeant EXPORTED
                        demande.setExported("PROCESSED");
                        telRepository.save(demande);
                        System.out.println("   ✅ Demande marquée comme traitée");

                        compteurTraitees++;

                    } else {
                        System.err.println("   ⚠️ Utilisateur non trouvé: " + demande.getPersoId());
                        compteurEchecs++;
                    }

                } catch (Exception e) {
                    System.err.println("   ❌ Erreur: " + e.getMessage());
                    e.printStackTrace();
                    compteurEchecs++;
                }
            }

            System.out.println("========================================");
            System.out.println("✅ TRAITEMENT TERMINÉ");
            System.out.println("   ✔️ Traitées: " + compteurTraitees);
            System.out.println("   ⏭️ Ignorées (après 11h10): " + compteurIgnorees);
            System.out.println("   ❌ Échecs: " + compteurEchecs);
            System.out.println("   📊 Total analysées: " + demandesValidees.size());
            System.out.println("========================================");

        } catch (Exception e) {
            System.err.println("❌ ERREUR GLOBALE: " + e.getMessage());
            e.printStackTrace();
        }
    }
}