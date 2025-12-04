package com.smldb2.demo.services;

import com.smldb2.demo.Entity.Remboursement;
import com.smldb2.demo.Entity.StatActDate;
import com.smldb2.demo.Entity.User;
import com.smldb2.demo.DTO.*;
import com.smldb2.demo.repositories.StatActDateRepository;
import com.smldb2.demo.repositories.UserRepository;
import com.smldb2.demo.repositories.RemboursementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StatActDateService {

    @Autowired
    private StatActDateRepository statActDateRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RemboursementRepository remboursementRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public List<String> getAllSocietes() {
        return statActDateRepository.findAllDistinctCodeSociete();
    }

    /**
     * Obtenir le libellé d'un rang
     */
    private String getLibelleRang(String rang) {
        if (rang == null || rang.trim().isEmpty()) {
            return "Non défini";
        }

        rang = rang.trim();
        String rangNormalise = rang.replaceFirst("^0+(?!$)", "");

        Map<String, String> libelles = new HashMap<>();
        libelles.put("0", "Adhérent");
        libelles.put("00", "Adhérent");
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
        libelles.put("90", "Conjoint");
        libelles.put("98", "Conjoint");
        libelles.put("99", "Conjoint");

        if (libelles.containsKey(rang)) {
            return libelles.get(rang);
        }
        if (libelles.containsKey(rangNormalise)) {
            return libelles.get(rangNormalise);
        }
        return "Rang " + rang;
    }

    /**
     * Récupère les statistiques par rang
     */
    public List<StatRangDTO> getStatistiquesParRang(
            String codeSociete,
            LocalDate dateDebut,
            LocalDate dateFin) {

        System.out.println("========================================");
        System.out.println("DÉBUT GÉNÉRATION STATISTIQUES PAR RANG");
        System.out.println("Société: " + codeSociete);
        System.out.println("Date début: " + dateDebut);
        System.out.println("Date fin: " + dateFin);
        System.out.println("========================================");

        Date debut = Date.from(dateDebut.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fin = Date.from(dateFin.atStartOfDay(ZoneId.systemDefault()).toInstant());

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
     * Récupère les statistiques par RIB
     */
    public List<StatRibDTO> getStatistiquesParRib(
            String codeSociete,
            LocalDate dateDebut,
            LocalDate dateFin) {

        System.out.println("----------------------------------------");
        System.out.println("DÉBUT TRAITEMENT STATISTIQUES PAR RIB");
        System.out.println("----------------------------------------");

        List<Object[]> statsRibs = statActDateRepository
                .findStatistiquesParRib(codeSociete, dateDebut, dateFin);

        System.out.println("📊 Nombre de résultats bruts de la requête: " + statsRibs.size());

        List<StatRibDTO> ribs = statsRibs.stream()
                .map(row -> {
                    String rib = (String) row[0];
                    Long nombreRemboursements = row[1] != null ? ((Number) row[1]).longValue() : 0L;
                    BigDecimal totalDepense = (BigDecimal) row[2];
                    BigDecimal totalRembourse = (BigDecimal) row[3];

                    BigDecimal difference = totalDepense.subtract(totalRembourse);
                    Double pourcentage = totalDepense.compareTo(BigDecimal.ZERO) > 0
                            ? totalRembourse.divide(totalDepense, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100)).doubleValue()
                            : 0.0;

                    return new StatRibDTO(rib, nombreRemboursements, totalDepense,
                            totalRembourse, difference, pourcentage);
                })
                .collect(Collectors.toList());

        System.out.println("----------------------------------------");
        System.out.println("✅ Nombre de RIBs après traitement: " + ribs.size());
        System.out.println("----------------------------------------");

        return ribs;
    }



    public ReportingResponseDTO getReportingBySocieteAndPeriode(
            String codeSociete,
            LocalDate dateDebut,
            LocalDate dateFin) {

        System.out.println("========================================");
        System.out.println("DÉBUT GÉNÉRATION REPORTING");
        System.out.println("Société: " + codeSociete);
        System.out.println("Date début: " + dateDebut);
        System.out.println("Date fin: " + dateFin);
        System.out.println("========================================");

        // Statistiques par adhérent
        List<Object[]> statsAdherents = statActDateRepository
                .findStatistiquesParAdherent(codeSociete, dateDebut, dateFin);

        System.out.println("✅ Stats adhérents récupérées: " + statsAdherents.size() + " lignes");

        List<StatAdherentDTO> adherents = statsAdherents.stream()
                .map(row -> {
                    String matriculeFromStat = (String) row[0];
                    String mat = (String) row[1];
                    String nomAdherent = (String) row[2];
                    String rang = (String) row[3];
                    BigDecimal depense = (BigDecimal) row[4];
                    BigDecimal rembourse = (BigDecimal) row[5];
                    Long nombreActes = ((Number) row[6]).longValue();

                    BigDecimal difference = depense.subtract(rembourse);
                    Double pourcentage = depense.compareTo(BigDecimal.ZERO) > 0
                            ? rembourse.divide(depense, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100)).doubleValue()
                            : 0.0;

                    Integer nombreFamille = 0;
                    BigDecimal plafondGlobal = BigDecimal.ZERO;

                    Optional<User> userOpt = userRepository.findByCin(mat != null ? mat : matriculeFromStat);
                    if (userOpt.isPresent()) {
                        User user = userOpt.get();
                        nombreFamille = user.getFamilles() != null ? user.getFamilles().size() : 0;
                        int totalPersonnes = 1 + nombreFamille;
                        plafondGlobal = BigDecimal.valueOf(7000000).multiply(BigDecimal.valueOf(totalPersonnes));
                    }

                    return new StatAdherentDTO(
                            mat != null ? mat : matriculeFromStat,
                            nomAdherent,
                            rang,
                            nombreFamille,
                            depense,
                            rembourse,
                            difference,
                            pourcentage,
                            nombreActes,
                            plafondGlobal
                    );
                })
                .collect(Collectors.toList());

        System.out.println("✅ Adhérents traités: " + adherents.size());

        // Statistiques par acte
        List<Object[]> statsActes = statActDateRepository
                .findStatistiquesActesBySocieteAndPeriode(codeSociete, dateDebut, dateFin);

        System.out.println("✅ Stats actes récupérées: " + statsActes.size() + " lignes");

        List<StatActeDTO> actes = statsActes.stream()
                .map(row -> {
                    String acte = (String) row[0];
                    BigDecimal depense = (BigDecimal) row[1];
                    BigDecimal rembourse = (BigDecimal) row[2];
                    Long nombreActes = ((Number) row[3]).longValue();

                    BigDecimal difference = depense.subtract(rembourse);
                    Double pourcentage = depense.compareTo(BigDecimal.ZERO) > 0
                            ? rembourse.divide(depense, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100)).doubleValue()
                            : 0.0;

                    return new StatActeDTO(acte, depense, rembourse,
                            difference, pourcentage, nombreActes);
                })
                .collect(Collectors.toList());

        System.out.println("✅ Actes traités: " + actes.size());

        // Statistiques par RIB
        List<StatRibDTO> ribs = getStatistiquesParRib(codeSociete, dateDebut, dateFin);

        // Statistiques par rang
        List<StatRangDTO> rangs = getStatistiquesParRang(codeSociete, dateDebut, dateFin);

        // Statistiques par MAT
        List<StatMatDTO> mats = getStatistiquesParMat(codeSociete, dateDebut, dateFin);

        // ✅ NOUVELLE LIGNE : Statistiques par COD_DOCT_CV
        List<StatCodDoctCvDTO> codDoctCvs = getStatistiquesParCodDoctCv(codeSociete, dateDebut, dateFin);

        // Calculs globaux
        BigDecimal totalDepense = adherents.stream()
                .map(StatAdherentDTO::getDepense)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalRembourse = adherents.stream()
                .map(StatAdherentDTO::getRembourse)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal difference = totalDepense.subtract(totalRembourse);

        Double pourcentageGlobal = totalDepense.compareTo(BigDecimal.ZERO) > 0
                ? totalRembourse.divide(totalDepense, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100)).doubleValue()
                : 0.0;

        Integer nombreActesTotal = adherents.stream()
                .map(StatAdherentDTO::getNombreActes)
                .mapToInt(Long::intValue)
                .sum();

        ReportingResponseDTO response = new ReportingResponseDTO(
                totalDepense,
                totalRembourse,
                difference,
                pourcentageGlobal,
                adherents.size(),
                nombreActesTotal,
                adherents,
                actes,
                ribs,
                rangs,
                mats,
                codDoctCvs // ✅ AJOUTÉ
        );

        System.out.println("========================================");
        System.out.println("RÉSUMÉ FINAL:");
        System.out.println("  - Adhérents: " + adherents.size());
        System.out.println("  - Actes: " + actes.size());
        System.out.println("  - RIBs: " + ribs.size());
        System.out.println("  - Rangs: " + rangs.size());
        System.out.println("  - MATs: " + mats.size());
        System.out.println("  - COD_DOCT_CV: " + codDoctCvs.size()); // ✅ AJOUTÉ
        System.out.println("  - Total dépense: " + totalDepense);
        System.out.println("  - Total remboursé: " + totalRembourse);
        System.out.println("  - Différence: " + difference);
        System.out.println("  - Pourcentage global: " + pourcentageGlobal + "%");
        System.out.println("========================================");

        return response;
    }

    public List<ActeDetailDTO> getActesByMatricule(
            String matricule,
            String codeSociete,
            LocalDate dateDebut,
            LocalDate dateFin) {

        List<StatActDate> actes = statActDateRepository
                .findActesByMatriculeAndPeriode(matricule, codeSociete, dateDebut, dateFin);

        return actes.stream()
                .map(acte -> {
                    String dateFormatted = acte.getDateBs().format(formatter);
                    Double pourcentage = acte.getDepense().compareTo(BigDecimal.ZERO) > 0
                            ? acte.getRembourse().divide(acte.getDepense(), 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100)).doubleValue()
                            : 0.0;

                    ActeDetailDTO dto = new ActeDetailDTO();
                    dto.setActe(acte.getActe());
                    dto.setDateBs(dateFormatted);
                    dto.setDepense(acte.getDepense());
                    dto.setRembourse(acte.getRembourse());
                    dto.setPourcentageRemboursement(pourcentage);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<String> getMatriculesBySocieteAndPeriode(
            String codeSociete,
            LocalDate dateDebut,
            LocalDate dateFin) {

        return statActDateRepository
                .findMatriculesBySocieteAndPeriode(codeSociete, dateDebut, dateFin);
    }


    // Ajoutez cette méthode dans StatActDateService.java

    /**
     * Récupère les statistiques par MAT (matricule prestataire)
     */
    public List<StatMatDTO> getStatistiquesParMat(
            String codeSociete,
            LocalDate dateDebut,
            LocalDate dateFin) {

        System.out.println("========================================");
        System.out.println("DÉBUT GÉNÉRATION STATISTIQUES PAR MAT");
        System.out.println("Société: " + codeSociete);
        System.out.println("Date début: " + dateDebut);
        System.out.println("Date fin: " + dateFin);
        System.out.println("========================================");

        Date debut = Date.from(dateDebut.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fin = Date.from(dateFin.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Object[]> results = remboursementRepository.findStatistiquesParMat(
                codeSociete, debut, fin);

        System.out.println("✅ Résultats bruts de la requête: " + results.size() + " MAT");

        List<StatMatDTO> statistics = results.stream()
                .map(row -> {
                    String mat = row[0] != null ? row[0].toString().trim() : "Non défini";
                    Long nombreRemboursements = ((Number) row[1]).longValue();
                    BigDecimal totalDepense = (BigDecimal) row[2];
                    BigDecimal totalRembourse = (BigDecimal) row[3];

                    BigDecimal difference = totalDepense.subtract(totalRembourse);
                    Double pourcentage = totalDepense.compareTo(BigDecimal.ZERO) > 0
                            ? totalRembourse.divide(totalDepense, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100)).doubleValue()
                            : 0.0;

                    System.out.println("📊 MAT '" + mat + "': " +
                            nombreRemboursements + " remboursements, " +
                            totalDepense + " DT dépensés");

                    return new StatMatDTO(
                            mat,
                            nombreRemboursements,
                            totalDepense,
                            totalRembourse,
                            difference,
                            pourcentage
                    );
                })
                .collect(Collectors.toList());

        System.out.println("========================================");
        System.out.println("✅ STATISTIQUES GÉNÉRÉES: " + statistics.size() + " MAT");
        System.out.println("========================================");

        return statistics;
    }

    /**
     * Récupère les remboursements détaillés pour un MAT spécifique
     */
    public List<Remboursement> getRemboursementsByMat(
            String codeSociete,
            LocalDate dateDebut,
            LocalDate dateFin,
            String mat) {

        Date debut = Date.from(dateDebut.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fin = Date.from(dateFin.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return remboursementRepository.findRemboursementsByMat(
                codeSociete, debut, fin, mat);
    }

    // Ajoutez ces méthodes dans StatActDateService.java

    /**
     * Récupère les statistiques par COD_DOCT_CV
     */
    public List<StatCodDoctCvDTO> getStatistiquesParCodDoctCv(
            String codeSociete,
            LocalDate dateDebut,
            LocalDate dateFin) {

        System.out.println("========================================");
        System.out.println("DÉBUT GÉNÉRATION STATISTIQUES PAR COD_DOCT_CV");
        System.out.println("Société: " + codeSociete);
        System.out.println("Date début: " + dateDebut);
        System.out.println("Date fin: " + dateFin);
        System.out.println("========================================");

        Date debut = Date.from(dateDebut.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fin = Date.from(dateFin.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Object[]> results = remboursementRepository.findStatistiquesParCodDoctCv(
                codeSociete, debut, fin);

        System.out.println("✅ Résultats bruts de la requête: " + results.size() + " COD_DOCT_CV");

        List<StatCodDoctCvDTO> statistics = results.stream()
                .map(row -> {
                    String codDoctCv = row[0] != null ? row[0].toString().trim() : "Non défini";
                    Long nombreRemboursements = ((Number) row[1]).longValue();
                    BigDecimal totalDepense = (BigDecimal) row[2];
                    BigDecimal totalRembourse = (BigDecimal) row[3];

                    BigDecimal difference = totalDepense.subtract(totalRembourse);
                    Double pourcentage = totalDepense.compareTo(BigDecimal.ZERO) > 0
                            ? totalRembourse.divide(totalDepense, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100)).doubleValue()
                            : 0.0;

                    System.out.println("📊 COD_DOCT_CV '" + codDoctCv + "': " +
                            nombreRemboursements + " remboursements, " +
                            totalDepense + " DT dépensés");

                    return new StatCodDoctCvDTO(
                            codDoctCv,
                            nombreRemboursements,
                            totalDepense,
                            totalRembourse,
                            difference,
                            pourcentage
                    );
                })
                .collect(Collectors.toList());

        System.out.println("========================================");
        System.out.println("✅ STATISTIQUES GÉNÉRÉES: " + statistics.size() + " COD_DOCT_CV");
        System.out.println("========================================");

        return statistics;
    }

    /**
     * Récupère les remboursements détaillés pour un COD_DOCT_CV spécifique
     */
    public List<Remboursement> getRemboursementsByCodDoctCv(
            String codeSociete,
            LocalDate dateDebut,
            LocalDate dateFin,
            String codDoctCv) {

        Date debut = Date.from(dateDebut.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fin = Date.from(dateFin.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return remboursementRepository.findRemboursementsByCodDoctCv(
                codeSociete, debut, fin, codDoctCv);
    }
}