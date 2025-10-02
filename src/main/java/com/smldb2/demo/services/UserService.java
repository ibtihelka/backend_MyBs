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

    /**
     * Obtenir les statistiques globales
     * SOLUTION TEMPORAIRE : Si vous n'avez pas de champ dateCreation
     */
    public UserStatsDTO getGlobalStats() {
        System.out.println("📊 Début de getGlobalStats");

        // Total des adhérents
        long total = userRepository.count();
        System.out.println("✅ Total adhérents: " + total);

        long nouveaux = 0;

        try {
            // TENTATIVE 1: Utiliser le champ dateCreation s'il existe
            LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
            Date startOfMonth = Date.from(firstDayOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());

            nouveaux = userRepository.countByDateCreationAfter(startOfMonth);
            System.out.println("✅ Nouveaux adhérents ce mois (via dateCreation): " + nouveaux);

        } catch (Exception e) {
            System.err.println("⚠️ Méthode countByDateCreationAfter non disponible: " + e.getMessage());
            nouveaux = 0;
            System.out.println("⚠️ Impossible de calculer les nouveaux adhérents sans champ dateCreation");
            System.out.println("💡 Ajoutez un champ 'dateCreation' à votre entité User pour activer cette fonctionnalité");
        }

        System.out.println("📊 Stats avant création DTO - Total: " + total + ", Nouveaux: " + nouveaux);

        // IMPORTANT: Vérifiez l'ordre des paramètres de votre constructeur UserStatsDTO
        // Si le constructeur est UserStatsDTO(long nouveaux, long total), inversez les paramètres ici
        UserStatsDTO stats = new UserStatsDTO(total, nouveaux);

        System.out.println("📊 Stats après création DTO - getTotal(): " + stats.getTotal() + ", getNouveaux(): " + stats.getNouveaux());

        return stats;
    }

    /**
     * VERSION ALTERNATIVE : Si vous avez un autre champ de date
     * Par exemple, si vous utilisez un champ existant comme 'datePieceIdentite' ou autre
     */
    public UserStatsDTO getGlobalStatsAlternative() {
        long total = userRepository.count();

        // Si vous voulez estimer les nouveaux adhérents basés sur un autre critère
        // Par exemple, les personnes de moins de 30 ans, ou basé sur persoId, etc.
        long nouveaux = 0;

        List<User> allUsers = userRepository.findAll();
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);

        for (User user : allUsers) {
            // EXEMPLE: Si votre persoId contient une date ou un timestamp
            // Adaptez cette logique selon votre structure de données
            // Par exemple: persoId = "2024001", "2024002", etc.

            // Pour l'instant, on retourne 0 car on ne peut pas le déterminer
        }

        return new UserStatsDTO(total, nouveaux);
    }

    /**
     * Obtenir les statistiques détaillées (par sexe et situation familiale)
     */
    public UserDetailedStatsDTO getDetailedStats() {
        System.out.println("📊 Début de getDetailedStats");

        List<User> allUsers = userRepository.findAll();
        System.out.println("✅ Nombre d'utilisateurs récupérés: " + allUsers.size());

        Map<String, Long> repartitionParSexe = new HashMap<>();
        repartitionParSexe.put("M", 0L);
        repartitionParSexe.put("F", 0L);
        repartitionParSexe.put("AUTRE", 0L);

        Map<String, Long> repartitionParSituationFamiliale = new HashMap<>();

        for (User user : allUsers) {
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



    public Map<String, Long> getMonthlyEvolution() {
        List<User> allUsers = userRepository.findAll();
        Map<String, Long> monthlyCount = new TreeMap<>();

        LocalDate now = LocalDate.now();
        for (int i = 11; i >= 0; i--) {
            LocalDate month = now.minusMonths(i);
            String monthKey = month.format(DateTimeFormatter.ofPattern("MMM yyyy", Locale.FRENCH));

            LocalDate startOfMonth = month.withDayOfMonth(1);
            LocalDate endOfMonth = month.withDayOfMonth(month.lengthOfMonth());

            Date startDate = Date.from(startOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(endOfMonth.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());

            long count = allUsers.stream()
                    .filter(u -> u.getDateCreation() != null)
                    .filter(u -> !u.getDateCreation().before(startDate) && !u.getDateCreation().after(endDate))
                    .count();

            monthlyCount.put(monthKey, count);
        }

        return monthlyCount;
    }
}