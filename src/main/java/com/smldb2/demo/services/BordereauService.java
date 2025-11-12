package com.smldb2.demo.services;

import com.smldb2.demo.DTO.*;
import com.smldb2.demo.Entity.*;
import com.smldb2.demo.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BordereauService {

    private final BordereauRepository bordereauRepository;
    private final RemboursementRepository remboursementRepository;
    private final FamilleRepository familleRepository;

    public BordereauService(
            BordereauRepository bordereauRepository,
            RemboursementRepository remboursementRepository,
            FamilleRepository familleRepository) {
        this.bordereauRepository = bordereauRepository;
        this.remboursementRepository = remboursementRepository;
        this.familleRepository = familleRepository;
    }

    // Récupérer tous les bordereaux
    public List<Bordereau> getAllBordereaux() {
        return bordereauRepository.findAll();
    }

    // Récupérer les bordereaux par préfixe
    public List<Bordereau> getBordereauxByPrefix(String prefix) {
        return bordereauRepository.findByRefBordereauStartingWith(prefix);
    }

    // Récupérer un bordereau avec ses remboursements
    public Optional<Bordereau> getBordereauAvecRemboursements(String refBordereau) {
        return bordereauRepository.findById(refBordereau);
    }

    // 📊 STATISTIQUES GLOBALES
    public GlobalStatsDTO getGlobalBordereauxStats(String prefix) {
        List<Bordereau> bordereaux;

        if (prefix != null && !prefix.isEmpty()) {
            bordereaux = getBordereauxByPrefix(prefix);
        } else {
            bordereaux = getAllBordereaux();
        }

        List<BordereauSummaryDTO> summaries = bordereaux.stream()
                .map(this::createBordereauSummary)
                .collect(Collectors.toList());

        BigDecimal totalDepense = summaries.stream()
                .map(BordereauSummaryDTO::getMontantDepense)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalRembourse = summaries.stream()
                .map(BordereauSummaryDTO::getMontantRembourse)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new GlobalStatsDTO(
                bordereaux.size(),
                totalDepense,
                totalRembourse,
                summaries
        );
    }

    private BordereauSummaryDTO createBordereauSummary(Bordereau bordereau) {
        List<Remboursement> rembs = bordereau.getRemboursements();

        BigDecimal depense = rembs.stream()
                .map(r -> r.getMntBs() != null ? r.getMntBs() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal rembourse = rembs.stream()
                .map(r -> r.getMntBsRemb() != null ? r.getMntBsRemb() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        double taux = depense.compareTo(BigDecimal.ZERO) > 0
                ? rembourse.divide(depense, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100)).doubleValue()
                : 0.0;

        return new BordereauSummaryDTO(
                bordereau.getRefBordereau(),
                bordereau.getDateBordereau(),
                rembs.size(),
                depense,
                rembourse,
                taux
        );
    }

    // 📊 STATISTIQUES DÉTAILLÉES D'UN BORDEREAU
    public BordereauStatsDTO getBordereauStats(String refBordereau) {
        Bordereau bordereau = bordereauRepository.findById(refBordereau)
                .orElseThrow(() -> new RuntimeException("Bordereau non trouvé"));

        List<Remboursement> remboursements = bordereau.getRemboursements();
        List<Famille> familles = familleRepository.findAll();

        return calculateBordereauStats(bordereau, remboursements, familles);
    }

    private BordereauStatsDTO calculateBordereauStats(
            Bordereau bordereau,
            List<Remboursement> remboursements,
            List<Famille> familles) {

        BordereauStatsDTO stats = new BordereauStatsDTO();

        // Infos de base
        stats.setRefBordereau(bordereau.getRefBordereau());
        stats.setDateBordereau(bordereau.getDateBordereau());
        stats.setTotalRemboursements(remboursements.size());

        // Map des familles par persoId et prénom
        Map<String, List<Famille>> familleMap = familles.stream()
                .collect(Collectors.groupingBy(Famille::getPersoId));

        // Calculs financiers
        BigDecimal totalDepense = BigDecimal.ZERO;
        BigDecimal totalRembourse = BigDecimal.ZERO;
        List<BigDecimal> montantsRemb = new ArrayList<>();

        // Compteurs de bénéficiaires
        int adherents = 0, conjoints = 0, enfants = 0, parents = 0;

        // Répartitions
        Map<String, BigDecimal> montantsParType = new HashMap<>();
        montantsParType.put("Adhérent", BigDecimal.ZERO);
        montantsParType.put("Conjoint", BigDecimal.ZERO);
        montantsParType.put("Enfant", BigDecimal.ZERO);
        montantsParType.put("Parent", BigDecimal.ZERO);

        Map<String, Integer> repartitionStatut = new HashMap<>();
        Map<String, Integer> repartitionSexe = new HashMap<>();
        Map<String, Integer> repartitionAge = new HashMap<>();

        // Initialiser les tranches d'âge
        repartitionAge.put("0-17", 0);
        repartitionAge.put("18-35", 0);
        repartitionAge.put("36-50", 0);
        repartitionAge.put("51-65", 0);
        repartitionAge.put("66+", 0);

        List<Integer> ages = new ArrayList<>();

        // Top adhérents
        Map<String, TopAdherentData> adherentData = new HashMap<>();

        // Parcourir les remboursements
        for (Remboursement r : remboursements) {
            BigDecimal depense = r.getMntBs() != null ? r.getMntBs() : BigDecimal.ZERO;
            BigDecimal rembourse = r.getMntBsRemb() != null ? r.getMntBsRemb() : BigDecimal.ZERO;

            totalDepense = totalDepense.add(depense);
            totalRembourse = totalRembourse.add(rembourse);

            if (rembourse.compareTo(BigDecimal.ZERO) > 0) {
                montantsRemb.add(rembourse);
            }

            // Statut
            String statut = r.getStatBs() != null ? r.getStatBs() : "Non défini";
            repartitionStatut.merge(statut, 1, Integer::sum);

            // Identifier le type de bénéficiaire
            String nomPrest = r.getNomPrenPrest() != null ? r.getNomPrenPrest().toLowerCase() : "";
            List<Famille> famillesUser = familleMap.getOrDefault(r.getPersoId(), new ArrayList<>());

            Famille famille = famillesUser.stream()
                    .filter(f -> f.getPrenomPrestataire().toLowerCase().equals(nomPrest))
                    .findFirst()
                    .orElse(null);

            String typeBenef = "Adhérent";

            if (famille != null) {
                TypePrestataire type = famille.getTypPrestataire();

                if (type == TypePrestataire.CONJOINT) {
                    conjoints++;
                    typeBenef = "Conjoint";
                } else if (type == TypePrestataire.ENFANT) {
                    enfants++;
                    typeBenef = "Enfant";
                } else if (type == TypePrestataire.PERE || type == TypePrestataire.MERE) {
                    parents++;
                    typeBenef = "Parent";
                } else {
                    adherents++;
                }

                // Sexe
                String sexe = famille.getSexe() != null ? famille.getSexe() : "Non défini";
                repartitionSexe.merge(sexe, 1, Integer::sum);

                // Âge
                if (famille.getDatNais() != null) {
                    LocalDate naissance = famille.getDatNais().toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDate();
                    int age = Period.between(naissance, LocalDate.now()).getYears();
                    ages.add(age);

                    String tranche = getTrancheAge(age);
                    repartitionAge.merge(tranche, 1, Integer::sum);
                }
            } else {
                adherents++;
            }

            // Montants par type
            montantsParType.merge(typeBenef, rembourse, BigDecimal::add);

            // Top adhérents
            String nomComplet = r.getNomPrenPrest() != null ? r.getNomPrenPrest() : "Inconnu";
            adherentData.computeIfAbsent(nomComplet, k -> new TopAdherentData())
                    .add(rembourse);
        }

        // Finaliser les stats
        stats.setMontantTotalDepense(totalDepense);
        stats.setMontantTotalRembourse(totalRembourse);

        double tauxMoyen = totalDepense.compareTo(BigDecimal.ZERO) > 0
                ? totalRembourse.divide(totalDepense, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100)).doubleValue()
                : 0.0;
        stats.setTauxRemboursementMoyen(tauxMoyen);

        stats.setNombreAdherents(adherents);
        stats.setNombreConjoints(conjoints);
        stats.setNombreEnfants(enfants);
        stats.setNombreParents(parents);

        // Montants moyens, min, max
        if (!montantsRemb.isEmpty()) {
            BigDecimal somme = montantsRemb.stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            stats.setMontantMoyenRemboursement(
                    somme.divide(BigDecimal.valueOf(montantsRemb.size()), 2, RoundingMode.HALF_UP)
            );
            stats.setMontantMaxRemboursement(
                    montantsRemb.stream().max(BigDecimal::compareTo).orElse(BigDecimal.ZERO)
            );
            stats.setMontantMinRemboursement(
                    montantsRemb.stream().min(BigDecimal::compareTo).orElse(BigDecimal.ZERO)
            );
        }

        stats.setRepartitionMontantsParType(montantsParType);
        stats.setRepartitionParStatut(repartitionStatut);
        stats.setRepartitionParSexe(repartitionSexe);
        stats.setRepartitionParTrancheAge(repartitionAge);

        // Moyenne d'âge
        if (!ages.isEmpty()) {
            int moyenneAge = (int) ages.stream()
                    .mapToInt(Integer::intValue)
                    .average()
                    .orElse(0);
            stats.setMoyenneAge(moyenneAge);
        }

        // Top 5 adhérents
        List<TopAdherentDTO> topAdherents = adherentData.entrySet().stream()
                .map(e -> new TopAdherentDTO(
                        e.getKey(),
                        e.getValue().count,
                        e.getValue().montant
                ))
                .sorted((a, b) -> b.getMontantTotal().compareTo(a.getMontantTotal()))
                .limit(5)
                .collect(Collectors.toList());
        stats.setTopAdherents(topAdherents);

        return stats;
    }

    private String getTrancheAge(int age) {
        if (age <= 17) return "0-17";
        if (age <= 35) return "18-35";
        if (age <= 50) return "36-50";
        if (age <= 65) return "51-65";
        return "66+";
    }

    // Classe interne pour agréger les données des adhérents
    private static class TopAdherentData {
        int count = 0;
        BigDecimal montant = BigDecimal.ZERO;

        void add(BigDecimal m) {
            count++;
            montant = montant.add(m);
        }
    }
}