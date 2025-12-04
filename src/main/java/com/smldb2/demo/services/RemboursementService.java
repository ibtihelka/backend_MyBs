package com.smldb2.demo.services;
import com.smldb2.demo.DTO.ContreVisiteDTO;
import com.smldb2.demo.DTO.MatStatDTO;
import com.smldb2.demo.Entity.RapportContreVisite;
import com.smldb2.demo.Entity.Remboursement;
import com.smldb2.demo.Entity.StatActDate;
import com.smldb2.demo.repositories.RapportContreVisiteRepository;
import com.smldb2.demo.repositories.RemboursementRepository;
import com.smldb2.demo.repositories.StatActDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RemboursementService {

    @Autowired
    private StatActDateRepository statActDateRepository;

    @Autowired
    private RapportContreVisiteRepository rapportContreVisiteRepository;

    @Autowired
    private RemboursementRepository remboursementRepository;

    public List<Remboursement> getAllRemboursements() {
        return remboursementRepository.findAll();
    }

    public Optional<Remboursement> getRemboursementById(String id) {
        return remboursementRepository.findById(id);
    }

    public List<Remboursement> getRemboursementsByPersoId(String persoId) {
        return remboursementRepository.findByPersoId(persoId);
    }

    public List<Remboursement> getRemboursementsByStatus(String status) {
        return remboursementRepository.findByStatBs(status);
    }

    public List<Remboursement> getRemboursementsByBordereau(String refBordereau) {
        return remboursementRepository.findByRefBordereau(refBordereau);
    }


    public Optional<Remboursement> getRemboursementWithActes(String refBsPhys) {
        return remboursementRepository.findById(refBsPhys);
    }

    // ✅ NOUVELLE MÉTHODE : Compter les remboursements avec cod_doct_cv non null
    public Long countRemboursementsWithCodDoctCv(String persoId) {
        return remboursementRepository.countRemboursementsWithCodDoctCv(persoId);
    }

    // ✅ NOUVELLE MÉTHODE : Récupérer les contre-visites avec statut et prévision
    public List<ContreVisiteDTO> getContreVisitesByPersoId(String persoId) {
        List<Remboursement> remboursements = remboursementRepository.findRemboursementsWithCodDoctCv(persoId);

        return remboursements.stream().map(r -> {
            ContreVisiteDTO dto = new ContreVisiteDTO();
            dto.setRefBsPhys(r.getRefBsPhys());
            dto.setCodDoctCv(r.getCodDoctCv());
            dto.setDatBs(r.getDatBs());
            dto.setDateBordereau(r.getDateBordereau());

            // Vérifier si un rapport de contre-visite existe pour ce BS
            Optional<RapportContreVisite> rapportOpt = rapportContreVisiteRepository.findFirstByRefBsPhys(r.getRefBsPhys());

            if (rapportOpt.isPresent()) {
                dto.setStatut("Oui");

                // Calculer la date de prévision : date du rapport + 15 jours
                RapportContreVisite rapport = rapportOpt.get();
                Date dateRapport = rapport.getDateRapport();

                if (dateRapport != null) {
                    LocalDate dateLocal = dateRapport.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();

                    LocalDate datePrevision = dateLocal.plusDays(15);

                    dto.setPrev(Date.from(datePrevision.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                } else {
                    dto.setPrev(null);
                }
            } else {
                dto.setStatut("Non");
                dto.setPrev(null); // Pas de prévision si statut "En attente"
            }

            return dto;
        }).collect(Collectors.toList());
    }

    // ✅ Calcul de la date de prévision
    private Date calculerDatePrevision(Date dateBordereau) {
        if (dateBordereau == null) {
            return null;
        }

        LocalDate dateLocal = dateBordereau.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        int jour = dateLocal.getDayOfMonth();
        LocalDate datePrev;

        if (jour > 10 && jour <= 25) {
            // Après le 10 → prévision le 25 du même mois
            datePrev = LocalDate.of(dateLocal.getYear(), dateLocal.getMonth(), 25);
        } else {
            // Après le 25 → prévision le 10 du mois suivant
            LocalDate moisSuivant = dateLocal.plusMonths(1);
            datePrev = LocalDate.of(moisSuivant.getYear(), moisSuivant.getMonth(), 10);
        }

        return Date.from(datePrev.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public List<Object[]> getDateBsNumBsMfByPersoId(String persoId) {

        // 1️⃣ Récupérer les REF_BS_PHYS
        List<String> refList = remboursementRepository.findRefBsByPersoId(persoId);

        List<Object[]> result = new ArrayList<>();

        // 2️⃣ Pour chaque REF_BS_PHYS → chercher dans stat_act_date
        for (String ref : refList) {
            List<StatActDate> stats = statActDateRepository.findValidStatByNumBs(ref);

            for (StatActDate s : stats) {
                result.add(new Object[]{
                        s.getDateBs(),
                        s.getNumBs(),
                        s.getMf()
                });
            }
        }

        return result;
    }

    public List<String> getMatriculesFiscalesByPersoAndBs(String persoId, String refBsPhys) {
        // On suppose que le persoId est utilisé pour filtrer les remboursements correspondants
        List<StatActDate> stats = statActDateRepository.findValidStatByNumBs(refBsPhys);

        // Retourner uniquement les MF distincts et non nuls
        return stats.stream()
                .map(StatActDate::getMf)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

}