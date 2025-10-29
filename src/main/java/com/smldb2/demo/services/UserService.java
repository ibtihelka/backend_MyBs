package com.smldb2.demo.services;

import com.smldb2.demo.DTO.UserDetailedStatsDTO;
import com.smldb2.demo.repositories.UserRepository;
import com.smldb2.demo.Entity.User;
import com.smldb2.demo.DTO.UserStatsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

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

    // ========== NOUVELLES MÉTHODES POUR RIB ET CONTACT ==========

    /**
     * Récupérer le RIB d'un utilisateur par son persoId
     */
    public String getRibByPersoId(String persoId) {
        try {
            return userRepository.findRibByPersoId(persoId);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération du RIB pour " + persoId + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Récupérer le contact d'un utilisateur par son persoId
     */
    public String getContactByPersoId(String persoId) {
        try {
            return userRepository.findContactByPersoId(persoId);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération du contact pour " + persoId + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Modifier le RIB d'un utilisateur
     */
    public boolean updateRib(String persoId, String newRib) {
        try {
            Optional<User> userOpt = userRepository.findByPersoId(persoId);

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setRib(newRib);
                userRepository.save(user);
                System.out.println("✅ RIB mis à jour avec succès pour " + persoId);
                return true;
            } else {
                System.err.println("❌ Utilisateur non trouvé: " + persoId);
                return false;
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la mise à jour du RIB pour " + persoId + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Modifier le contact d'un utilisateur
     */
    public boolean updateContact(String persoId, String newContact) {
        try {
            Optional<User> userOpt = userRepository.findByPersoId(persoId);

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setContact(newContact);
                userRepository.save(user);
                System.out.println("✅ Contact mis à jour avec succès pour " + persoId);
                return true;
            } else {
                System.err.println("❌ Utilisateur non trouvé: " + persoId);
                return false;
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la mise à jour du contact pour " + persoId + ": " + e.getMessage());
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

}