package com.smldb2.demo.services;

import com.smldb2.demo.Entity.StatActDate;
import com.smldb2.demo.Entity.User;
import com.smldb2.demo.DTO.*;
import com.smldb2.demo.repositories.StatActDateRepository;
import com.smldb2.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StatActDateService {

    @Autowired
    private StatActDateRepository statActDateRepository;

    @Autowired
    private UserRepository userRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Récupère la liste de toutes les sociétés disponibles
     */
    public List<String> getAllSocietes() {
        return statActDateRepository.findAllDistinctCodeSociete();
    }

    /**
     * Récupère le reporting complet par société et période
     */
    public ReportingResponseDTO getReportingBySocieteAndPeriode(
            String codeSociete,
            LocalDate dateDebut,
            LocalDate dateFin) {

        // Statistiques par adhérent
        List<Object[]> statsAdherents = statActDateRepository
                .findStatistiquesParAdherent(codeSociete, dateDebut, dateFin);

        List<StatAdherentDTO> adherents = statsAdherents.stream()
                .map(row -> {
                    String matricule = (String) row[0];
                    BigDecimal depense = (BigDecimal) row[1];
                    BigDecimal rembourse = (BigDecimal) row[2];
                    Long nombreActes = (Long) row[3];

                    BigDecimal difference = depense.subtract(rembourse);
                    Double pourcentage = depense.compareTo(BigDecimal.ZERO) > 0
                            ? rembourse.divide(depense, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100)).doubleValue()
                            : 0.0;

                    // Récupérer les informations de l'adhérent depuis la table User
                    String nomAdherent = "";
                    Integer nombreFamille = 0;
                    BigDecimal plafondGlobal = BigDecimal.ZERO;

                    Optional<User> userOpt = userRepository.findByCin(matricule);
                    if (userOpt.isPresent()) {
                        User user = userOpt.get();
                        nomAdherent = user.getPersoName();
                        nombreFamille = user.getFamilles() != null ? user.getFamilles().size() : 0;

                        // Calcul du plafond global (exemple: 7 000 000 par personne)
                        // 1 adhérent + nombre de famille
                        int totalPersonnes = 1 + nombreFamille;
                        plafondGlobal = BigDecimal.valueOf(7000000).multiply(BigDecimal.valueOf(totalPersonnes));
                    }

                    return new StatAdherentDTO(matricule, nomAdherent, nombreFamille,
                            depense, rembourse, difference, pourcentage, nombreActes, plafondGlobal);
                })
                .collect(Collectors.toList());

        // Statistiques par acte
        List<Object[]> statsActes = statActDateRepository
                .findStatistiquesActesBySocieteAndPeriode(codeSociete, dateDebut, dateFin);

        List<StatActeDTO> actes = statsActes.stream()
                .map(row -> {
                    String acte = (String) row[0];
                    BigDecimal depense = (BigDecimal) row[1];
                    BigDecimal rembourse = (BigDecimal) row[2];
                    Long nombreActes = (Long) row[3];

                    BigDecimal difference = depense.subtract(rembourse);
                    Double pourcentage = depense.compareTo(BigDecimal.ZERO) > 0
                            ? rembourse.divide(depense, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100)).doubleValue()
                            : 0.0;

                    return new StatActeDTO(acte, depense, rembourse,
                            difference, pourcentage, nombreActes);
                })
                .collect(Collectors.toList());

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

        return new ReportingResponseDTO(
                totalDepense,
                totalRembourse,
                difference,
                pourcentageGlobal,
                adherents.size(),
                nombreActesTotal,
                adherents,
                actes
        );
    }

    /**
     * Récupère les actes détaillés d'un adhérent
     */
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

                    // Utiliser le constructeur avec les bons types
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

    /**
     * Récupère la liste des matricules ayant des remboursements
     */
    public List<String> getMatriculesBySocieteAndPeriode(
            String codeSociete,
            LocalDate dateDebut,
            LocalDate dateFin) {

        return statActDateRepository
                .findMatriculesBySocieteAndPeriode(codeSociete, dateDebut, dateFin);
    }
}