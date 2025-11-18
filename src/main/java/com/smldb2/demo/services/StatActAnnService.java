package com.smldb2.demo.services;

import com.smldb2.demo.DTO.*;
import com.smldb2.demo.Entity.Famille;
import com.smldb2.demo.Entity.Remboursement;
import com.smldb2.demo.Entity.User;
import com.smldb2.demo.repositories.RemboursementRepository;
import com.smldb2.demo.repositories.StatActAnnRepository;
import com.smldb2.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatActAnnService {

    @Autowired
    private StatActAnnRepository statActAnnRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RemboursementRepository remboursementRepository;

    public List<Integer> getAllAnnees() {
        return statActAnnRepository.findAllDistinctAnnees();
    }

    public List<String> getAllSocietes() {
        return statActAnnRepository.findAllDistinctSocietes();
    }

    public ReportingAnnuelDTO getReportingAnnuel(Integer annee, String codeSociete) {

        // --- Statistiques par actes (déjà existant) ---
        List<Object[]> statsActes = statActAnnRepository.findStatistiquesActesByAnneeAndSociete(annee, codeSociete);

        List<ActeAnnuelDTO> actes = statsActes.stream()
                .map(row -> {
                    String acte = (String) row[0];
                    BigDecimal depense = row[1] != null ? (BigDecimal) row[1] : BigDecimal.ZERO;
                    BigDecimal rembourse = row[2] != null ? (BigDecimal) row[2] : BigDecimal.ZERO;
                    BigDecimal difference = depense.subtract(rembourse);
                    Double pourcentage = depense.compareTo(BigDecimal.ZERO) > 0
                            ? rembourse.divide(depense, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue()
                            : 0.0;
                    return new ActeAnnuelDTO(acte, depense, rembourse, difference, pourcentage);
                })
                .collect(Collectors.toList());

        // Statistiques globales
        List<Object[]> statsGlobalesList = statActAnnRepository.findStatistiquesGlobales(annee, codeSociete);
        BigDecimal totalDepense = BigDecimal.ZERO;
        BigDecimal totalRembourse = BigDecimal.ZERO;
        Long nombreActes = 0L;

        if (!statsGlobalesList.isEmpty()) {
            Object[] statsGlobales = statsGlobalesList.get(0);
            totalDepense = statsGlobales[0] != null ? (BigDecimal) statsGlobales[0] : BigDecimal.ZERO;
            totalRembourse = statsGlobales[1] != null ? (BigDecimal) statsGlobales[1] : BigDecimal.ZERO;
            nombreActes = statsGlobales[2] != null ? ((Number) statsGlobales[2]).longValue() : 0L;
        }

        BigDecimal difference = totalDepense.subtract(totalRembourse);
        Double pourcentageGlobal = totalDepense.compareTo(BigDecimal.ZERO) > 0
                ? totalRembourse.divide(totalDepense, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue()
                : 0.0;

        // --- Statistiques des adhérents et familles ---
        StatistiquesPrestataireDTO statsPrestataire = calculerStatistiquesPrestataire(annee, codeSociete);

        return new ReportingAnnuelDTO(
                annee,
                codeSociete,
                totalDepense,
                totalRembourse,
                difference,
                pourcentageGlobal,
                nombreActes.intValue(),
                actes,
                statsPrestataire
        );
    }

    private StatistiquesPrestataireDTO calculerStatistiquesPrestataire(Integer annee, String codeSociete) {

        // Récupérer tous les utilisateurs actifs
        List<User> usersActifs = userRepository.findAll().stream()
                .filter(u -> "ACTIF".equalsIgnoreCase(u.getPosition()))
                .collect(Collectors.toList());

        int nombreAdherents = 0;

        // Compteurs pour les enfants et conjoints
        int enfant01Count = 0;
        int enfant02Count = 0;
        int enfant03Count = 0;
        int enfant04Count = 0;
        int enfant05Count = 0;
        int conjoint98Count = 0;
        int conjoint99Count = 0;

        // Compteur pour l'adhérent lui-même
        int adherentCount = 0;

        for (User user : usersActifs) {
            // Récupérer tous les remboursements de cet utilisateur pour l'année et la société
            List<Remboursement> remboursementsUser = user.getRemboursements().stream()
                    .filter(r -> codeSociete.equalsIgnoreCase(r.getCodeEntreprise())
                            && r.getDatBs() != null
                            && getYear(r.getDatBs()) == annee)
                    .collect(Collectors.toList());

            if (!remboursementsUser.isEmpty()) {
                nombreAdherents++;

                // Compter les remboursements de l'adhérent lui-même
                long adherentRemb = remboursementsUser.stream()
                        .filter(r -> r.getNomPrenPrest() != null &&
                                r.getNomPrenPrest().equalsIgnoreCase(user.getPersoName()))
                        .count();
                adherentCount += adherentRemb;

                // Récupérer les membres de la famille
                List<Famille> familles = user.getFamilles();

                // Séparer les enfants et conjoints
                List<Famille> enfants = familles.stream()
                        .filter(f -> f.getTypPrestataire() != null &&
                                "ENFANT".equalsIgnoreCase(f.getTypPrestataire().name()))
                        .collect(Collectors.toList());

                List<Famille> conjoints = familles.stream()
                        .filter(f -> f.getTypPrestataire() != null &&
                                "CONJOINT".equalsIgnoreCase(f.getTypPrestataire().name()))
                        .collect(Collectors.toList());

                // Traiter les enfants (jusqu'à 5)
                for (int i = 0; i < Math.min(enfants.size(), 5); i++) {
                    Famille enfant = enfants.get(i);
                    long countRemb = remboursementsUser.stream()
                            .filter(r -> r.getNomPrenPrest() != null &&
                                    r.getNomPrenPrest().equalsIgnoreCase(enfant.getPrenomPrestataire()))
                            .count();

                    switch (i) {
                        case 0: enfant01Count += countRemb; break;
                        case 1: enfant02Count += countRemb; break;
                        case 2: enfant03Count += countRemb; break;
                        case 3: enfant04Count += countRemb; break;
                        case 4: enfant05Count += countRemb; break;
                    }
                }

                // Traiter les conjoints (jusqu'à 2)
                for (int i = 0; i < Math.min(conjoints.size(), 2); i++) {
                    Famille conjoint = conjoints.get(i);
                    long countRemb = remboursementsUser.stream()
                            .filter(r -> r.getNomPrenPrest() != null &&
                                    r.getNomPrenPrest().equalsIgnoreCase(conjoint.getPrenomPrestataire()))
                            .count();

                    if (i == 0) {
                        conjoint98Count += countRemb;
                    } else {
                        conjoint99Count += countRemb;
                    }
                }
            }
        }

        return new StatistiquesPrestataireDTO(
                nombreAdherents,
                adherentCount,      // Fréquence des adhérents (leurs propres remboursements)
                enfant01Count,
                enfant02Count,
                enfant03Count,
                enfant04Count,
                enfant05Count,
                conjoint98Count,
                conjoint99Count
        );
    }

    // Helper pour extraire l'année d'une date
    private int getYear(Date date) {
        if (date == null) return 0;
        return date.toInstant().atZone(ZoneId.systemDefault()).getYear();
    }
}