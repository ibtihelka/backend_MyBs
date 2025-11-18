package com.smldb2.demo.services;


import com.smldb2.demo.Entity.Acte;
import com.smldb2.demo.Entity.Remboursement;

import com.smldb2.demo.repositories.ActeRepository;
import com.smldb2.demo.repositories.RemboursementRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ActeAffectationService {

    @Autowired
    private ActeRepository acteRepository;

    @Autowired
    private RemboursementRepository remboursementRepository;

    /**
     * Affecte tous les actes existants à leurs remboursements correspondants
     * en fonction du REF_BS_PHYS
     */
    @Transactional
    public Map<String, Object> affecterTousLesActes() {
        List<Acte> tousLesActes = acteRepository.findAll();
        int actesAffectes = 0;
        int actesNonAffectes = 0;

        for (Acte acte : tousLesActes) {
            if (acte.getRefBsPhys() != null) {
                Optional<Remboursement> remboursementOpt =
                        remboursementRepository.findById(acte.getRefBsPhys());

                if (remboursementOpt.isPresent()) {
                    Remboursement remboursement = remboursementOpt.get();
                    acte.setRemboursement(remboursement);
                    acteRepository.save(acte);
                    actesAffectes++;
                } else {
                    actesNonAffectes++;
                }
            } else {
                actesNonAffectes++;
            }
        }

        Map<String, Object> resultat = new HashMap<>();
        resultat.put("total_actes", tousLesActes.size());
        resultat.put("actes_affectes", actesAffectes);
        resultat.put("actes_non_affectes", actesNonAffectes);
        resultat.put("message", "Affectation terminée avec succès");

        return resultat;
    }

    /**
     * Affecte les actes d'un remboursement spécifique
     */
    @Transactional
    public Map<String, Object> affecterActesPourRemboursement(String refBsPhys) {
        Optional<Remboursement> remboursementOpt = remboursementRepository.findById(refBsPhys);

        if (!remboursementOpt.isPresent()) {
            Map<String, Object> erreur = new HashMap<>();
            erreur.put("erreur", "Remboursement non trouvé avec REF_BS_PHYS: " + refBsPhys);
            return erreur;
        }

        Remboursement remboursement = remboursementOpt.get();
        List<Acte> actes = acteRepository.findByRefBsPhys(refBsPhys);

        for (Acte acte : actes) {
            acte.setRemboursement(remboursement);
            acteRepository.save(acte);
        }

        Map<String, Object> resultat = new HashMap<>();
        resultat.put("ref_bs_phys", refBsPhys);
        resultat.put("nombre_actes_affectes", actes.size());
        resultat.put("message", "Actes affectés au remboursement avec succès");

        return resultat;
    }

    /**
     * Affecte un acte spécifique à un remboursement
     */
    @Transactional
    public Map<String, Object> affecterActeUnique(Long idActe, String refBsPhys) {
        Optional<Acte> acteOpt = acteRepository.findById(idActe);
        Optional<Remboursement> remboursementOpt = remboursementRepository.findById(refBsPhys);

        Map<String, Object> resultat = new HashMap<>();

        if (!acteOpt.isPresent()) {
            resultat.put("erreur", "Acte non trouvé avec ID: " + idActe);
            return resultat;
        }

        if (!remboursementOpt.isPresent()) {
            resultat.put("erreur", "Remboursement non trouvé avec REF_BS_PHYS: " + refBsPhys);
            return resultat;
        }

        Acte acte = acteOpt.get();
        Remboursement remboursement = remboursementOpt.get();

        acte.setRemboursement(remboursement);
        acte.setRefBsPhys(refBsPhys);
        acteRepository.save(acte);

        resultat.put("id_acte", idActe);
        resultat.put("ref_bs_phys", refBsPhys);
        resultat.put("message", "Acte affecté avec succès");

        return resultat;
    }

    /**
     * Vérifie l'état d'affectation des actes
     */
    public Map<String, Object> verifierEtatAffectation() {
        List<Acte> tousLesActes = acteRepository.findAll();
        long actesAffectes = tousLesActes.stream()
                .filter(acte -> acte.getRemboursement() != null)
                .count();
        long actesNonAffectes = tousLesActes.size() - actesAffectes;

        Map<String, Object> etat = new HashMap<>();
        etat.put("total_actes", tousLesActes.size());
        etat.put("actes_affectes", actesAffectes);
        etat.put("actes_non_affectes", actesNonAffectes);
        etat.put("pourcentage_affectation",
                tousLesActes.size() > 0 ? (actesAffectes * 100.0 / tousLesActes.size()) : 0);

        return etat;
    }

    /**
     * Désaffecte un acte (supprime le lien avec le remboursement)
     */
    @Transactional
    public Map<String, Object> desaffecterActe(Long idActe) {
        Optional<Acte> acteOpt = acteRepository.findById(idActe);
        Map<String, Object> resultat = new HashMap<>();

        if (!acteOpt.isPresent()) {
            resultat.put("erreur", "Acte non trouvé avec ID: " + idActe);
            return resultat;
        }

        Acte acte = acteOpt.get();
        acte.setRemboursement(null);
        acteRepository.save(acte);

        resultat.put("id_acte", idActe);
        resultat.put("message", "Acte désaffecté avec succès");

        return resultat;
    }
}
