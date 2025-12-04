package com.smldb2.demo.services;

import com.smldb2.demo.DTO.StatMfDTO;
import com.smldb2.demo.DTO.StatRangDTO;
import com.smldb2.demo.DTO.StatistiqueGeneraleDTO;
import com.smldb2.demo.Entity.Remboursement;
import com.smldb2.demo.repositories.RemboursementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatRangService {

    @Autowired
    private RemboursementRepository remboursementRepository;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Récupère toutes les sociétés (codes entreprise) disponibles
     */
    public List<String> getAllSocietes() {
        return remboursementRepository.findAllDistinctCodeEntreprise();
    }

    /**
     * Obtenir le libellé d'un rang
     */
    private String getLibelleRang(String rang) {
        if (rang == null || rang.trim().isEmpty()) {
            return "Non défini";
        }

        // Trim pour supprimer les espaces
        rang = rang.trim();

        // Normaliser le rang (supprimer les zéros à gauche sauf si c'est "0" ou "00")
        String rangNormalise = rang.replaceFirst("^0+(?!$)", "");

        Map<String, String> libelles = new HashMap<>();
        // Adhérent
        libelles.put("0", "Adhérent");
        libelles.put("00", "Adhérent");

        // Enfants
        libelles.put("1", "Enfant 1");
        libelles.put("01", "Enfant 1");
        libelles.put("2", "Enfant 2");
        libelles.put("02", "Enfant 2");
        libelles.put("3", "Enfant 3");
        libelles.put("03", "Enfant 3");
        libelles.put("4", "Enfant 4");
        libelles.put("04", "Enfant 4");
        libelles.put("5", "Enfant 5");
        libelles.put("05", "Enfant 5");

        // Conjoint
        libelles.put("90", "Conjoint");
        libelles.put("98", "Conjoint");
        libelles.put("99", "Conjoint");

        // Chercher d'abord avec le rang original
        if (libelles.containsKey(rang)) {
            return libelles.get(rang);
        }

        // Ensuite avec le rang normalisé
        if (libelles.containsKey(rangNormalise)) {
            return libelles.get(rangNormalise);
        }

        // Si aucune correspondance, retourner le libellé par défaut
        return "Rang " + rang;
    }

    /**
     * Récupère les statistiques par rang
     */
    public List<StatRangDTO> getStatistiquesParRang(
            String codeSociete,
            String dateDebut,
            String dateFin) throws ParseException {

        System.out.println("========================================");
        System.out.println("DÉBUT GÉNÉRATION STATISTIQUES PAR RANG");
        System.out.println("Société: " + codeSociete);
        System.out.println("Date début: " + dateDebut);
        System.out.println("Date fin: " + dateFin);
        System.out.println("========================================");

        Date debut = dateFormat.parse(dateDebut);
        Date fin = dateFormat.parse(dateFin);

        List<Object[]> results = remboursementRepository.findStatistiquesParRang(
                codeSociete, debut, fin);

        System.out.println("✅ Résultats bruts de la requête: " + results.size() + " lignes");

        List<StatRangDTO> statistics = results.stream()
                .map(row -> {
                    String rang = row[0] != null ? row[0].toString().trim() : null;
                    Long nombreRemboursements = ((Number) row[1]).longValue();
                    BigDecimal totalDepense = (BigDecimal) row[2];
                    BigDecimal totalRembourse = (BigDecimal) row[3];
                    Long nombreActes = ((Number) row[4]).longValue();

                    BigDecimal difference = totalDepense.subtract(totalRembourse);
                    Double pourcentage = totalDepense.compareTo(BigDecimal.ZERO) > 0
                            ? totalRembourse.divide(totalDepense, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100)).doubleValue()
                            : 0.0;

                    String libelleRang = getLibelleRang(rang);

                    System.out.println("📊 Rang '" + rang + "' → " + libelleRang + ": " +
                            nombreRemboursements + " remboursements, " +
                            totalDepense + " DT dépensés");

                    return new StatRangDTO(
                            rang,
                            libelleRang,
                            nombreRemboursements,
                            totalDepense,
                            totalRembourse,
                            difference,
                            pourcentage,
                            nombreActes
                    );
                })
                .collect(Collectors.toList());

        System.out.println("========================================");
        System.out.println("✅ STATISTIQUES GÉNÉRÉES: " + statistics.size() + " rangs");
        System.out.println("========================================");

        return statistics;
    }

    /**
     * Récupère les détails des remboursements pour un rang spécifique
     */
    public List<Remboursement> getRemboursementsByRang(
            String rang,
            String codeSociete,
            String dateDebut,
            String dateFin) throws ParseException {

        Date debut = dateFormat.parse(dateDebut);
        Date fin = dateFormat.parse(dateFin);

        return remboursementRepository.findRemboursementsByRang(
                codeSociete, debut, fin, rang);
    }

    /**
     * Récupère les statistiques générales
     */
    public StatistiqueGeneraleDTO getStatistiquesGenerales(
            String codeSociete,
            String dateDebut,
            String dateFin) throws ParseException {

        Date debut = dateFormat.parse(dateDebut);
        Date fin = dateFormat.parse(dateFin);

        Object[] result = remboursementRepository.findStatistiquesGenerales(
                codeSociete, debut, fin);

        if (result == null) {
            return new StatistiqueGeneraleDTO(0L, 0L, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        Long nombreTotalRemboursements = ((Number) result[0]).longValue();
        Long nombreTotalActes = ((Number) result[1]).longValue();
        BigDecimal montantTotalDepense = (BigDecimal) result[2];
        BigDecimal montantTotalRembourse = (BigDecimal) result[3];

        return new StatistiqueGeneraleDTO(
                nombreTotalRemboursements,
                nombreTotalActes,
                montantTotalDepense,
                montantTotalRembourse
        );
    }

    public List<StatMfDTO> getStatistiquesParMf(String codeSociete, String dateDebut, String dateFin) throws ParseException {
        Date debut = dateFormat.parse(dateDebut);
        Date fin = dateFormat.parse(dateFin);

        List<Object[]> results = remboursementRepository.findStatistiquesParMf(codeSociete, debut, fin);

        return results.stream()
                .map(row -> {
                    String mf = row[0] != null ? row[0].toString() : "N/A";
                    Long nombreRemboursements = ((Number) row[1]).longValue();
                    BigDecimal totalRembourse = (BigDecimal) row[2];
                    return new StatMfDTO(mf, nombreRemboursements, totalRembourse);
                })
                .collect(Collectors.toList());
    }

}