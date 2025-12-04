package com.smldb2.demo.services;

import com.smldb2.demo.DTO.StatistiquesAdherantDTO;
import com.smldb2.demo.Entity.Famille;
import com.smldb2.demo.Entity.Reclamation;
import com.smldb2.demo.Entity.Remboursement;
import com.smldb2.demo.Entity.TypePrestataire;
import com.smldb2.demo.repositories.FamilleRepository;
import com.smldb2.demo.repositories.ReclamationRepository;
import com.smldb2.demo.repositories.RemboursementRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatistiquesService {



    @Autowired
    private RemboursementRepository remboursementRepository;

    @Autowired
    private ReclamationRepository reclamationRepository;

    @Autowired
    private FamilleRepository familleRepository;

    public StatistiquesAdherantDTO getStatistiquesAdherant(String persoId, Integer annee) {
        if (annee == null) {
            annee = LocalDate.now().getYear();
        }

        StatistiquesAdherantDTO stats = new StatistiquesAdherantDTO();
        stats.setAnnee(annee);

        try {
            // === STATISTIQUES GLOBALES (toutes années) ===
            stats.setTotalRemboursements(remboursementRepository.countByPersoId(persoId));
            stats.setTotalReclamations(reclamationRepository.countByPersoId(persoId));
            stats.setTotalContreVisites(remboursementRepository.countRemboursementsWithCodDoctCv(persoId));

            // === STATISTIQUES PAR ANNÉE ===
            stats.setRemboursementsAnnee(remboursementRepository.countByPersoIdAndYear(persoId, annee));
            stats.setReclamationsAnnee(reclamationRepository.countByPersoIdAndYear(persoId, annee));
            stats.setContreVisitesAnnee(remboursementRepository.countContreVisitesByPersoIdAndYear(persoId, annee));

            // === RÉCLAMATIONS DÉTAILLÉES ===
            stats.setReclamationsAvecReponse(reclamationRepository.countReclamationsAvecReponseByYear(persoId, annee));
            stats.setReclamationsSansReponse(reclamationRepository.countReclamationsSansReponseByYear(persoId, annee));

            // === RÉPARTITION PAR TYPE ===
            stats.setRepartitionParType(getRepartitionParType(persoId, annee));

            // === ÉVOLUTION MENSUELLE ===
            stats.setRemboursementsParMois(getRemboursementsParMois(persoId, annee));
            stats.setReclamationsParMois(getReclamationsParMois(persoId, annee));
            stats.setContreVisitesParMois(getContreVisitesParMois(persoId, annee));

            // === ÉVOLUTION ANNUELLE ===
            stats.setEvolutionAnnuelleRemboursements(getEvolutionAnnuelleRemboursements(persoId));
            stats.setEvolutionAnnuelleReclamations(getEvolutionAnnuelleReclamations(persoId));
            stats.setEvolutionAnnuelleContreVisites(getEvolutionAnnuelleContreVisites(persoId));

            // === STATISTIQUES SUPPLÉMENTAIRES ===
            stats.setMoisPlusActif(getMoisPlusActif(stats.getRemboursementsParMois()));
            stats.setTauxContreVisite(calculerTauxContreVisite(
                    stats.getContreVisitesAnnee(),
                    stats.getRemboursementsAnnee()
            ));

        } catch (Exception e) {
            System.err.println("❌ Erreur lors du calcul des statistiques: " + e.getMessage());
            e.printStackTrace();
        }

        return stats;
    }

    private Map<String, Long> getRepartitionParType(String persoId, Integer annee) {
        Map<String, Long> repartition = new HashMap<>();
        List<Remboursement> remboursements = remboursementRepository.findByPersoIdAndYear(persoId, annee);
        List<Famille> famille = familleRepository.findByPersoId(persoId);

        Map<String, TypePrestataire> prenomToType = famille.stream()
                .collect(Collectors.toMap(
                        Famille::getPrenomPrestataire,
                        Famille::getTypPrestataire,
                        (existing, replacement) -> existing
                ));

        long adherant = 0, conjoint = 0, enfant = 0;

        for (Remboursement r : remboursements) {
            String nomPrenPrest = r.getNomPrenPrest();
            if (nomPrenPrest == null || nomPrenPrest.trim().isEmpty()) {
                adherant++;
            } else {
                TypePrestataire type = prenomToType.get(nomPrenPrest);
                if (type == TypePrestataire.CONJOINT) {
                    conjoint++;
                } else if (type == TypePrestataire.ENFANT) {
                    enfant++;
                } else {
                    adherant++;
                }
            }
        }

        repartition.put("adherant", adherant);
        repartition.put("conjoint", conjoint);
        repartition.put("enfant", enfant);
        return repartition;
    }

    private Map<String, Long> getRemboursementsParMois(String persoId, Integer annee) {
        return getDataParMois(
                remboursementRepository.findByPersoIdAndYear(persoId, annee),
                Remboursement::getDatBs
        );
    }

    private Map<String, Long> getReclamationsParMois(String persoId, Integer annee) {
        return getDataParMois(
                reclamationRepository.findByPersoIdAndYear(persoId, annee),
                Reclamation::getDateCreation
        );
    }

    private Map<String, Long> getContreVisitesParMois(String persoId, Integer annee) {
        List<Remboursement> contreVisites = remboursementRepository.findByPersoIdAndYear(persoId, annee)
                .stream()
                .filter(r -> r.getCodDoctCv() != null && !r.getCodDoctCv().trim().isEmpty())
                .collect(Collectors.toList());

        return getDataParMois(contreVisites, Remboursement::getDatBs);
    }

    private <T> Map<String, Long> getDataParMois(List<T> data, java.util.function.Function<T, Date> dateExtractor) {
        Map<String, Long> parMois = new LinkedHashMap<>();
        String[] mois = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};

        for (String m : mois) {
            parMois.put(m, 0L);
        }

        for (T item : data) {
            Date date = dateExtractor.apply(item);
            if (date != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int moisIndex = cal.get(Calendar.MONTH);
                String nomMois = mois[moisIndex];
                parMois.put(nomMois, parMois.get(nomMois) + 1);
            }
        }

        return parMois;
    }

    private Map<Integer, Long> getEvolutionAnnuelleRemboursements(String persoId) {
        return getEvolutionAnnuelle(
                remboursementRepository.findByPersoId(persoId),
                Remboursement::getDatBs
        );
    }



    private Map<Integer, Long> getEvolutionAnnuelleReclamations(String persoId) {
        return getEvolutionAnnuelle(
                reclamationRepository.findAllByPersoIdOrderByDate(persoId),
                Reclamation::getDateCreation
        );
    }

    private Map<Integer, Long> getEvolutionAnnuelleContreVisites(String persoId) {
        List<Remboursement> contreVisites = remboursementRepository.findByPersoId(persoId)
                .stream()
                .filter(r -> r.getCodDoctCv() != null && !r.getCodDoctCv().trim().isEmpty())
                .collect(Collectors.toList());

        return getEvolutionAnnuelle(contreVisites, Remboursement::getDatBs);
    }

    private <T> Map<Integer, Long> getEvolutionAnnuelle(List<T> data, java.util.function.Function<T, Date> dateExtractor) {
        Map<Integer, Long> evolution = new TreeMap<>();

        for (T item : data) {
            Date date = dateExtractor.apply(item);
            if (date != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int annee = cal.get(Calendar.YEAR);
                evolution.put(annee, evolution.getOrDefault(annee, 0L) + 1);
            }
        }

        return evolution;
    }

    private String getMoisPlusActif(Map<String, Long> remboursementsParMois) {
        return remboursementsParMois.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey() + " (" + entry.getValue() + ")")
                .orElse("Aucun");
    }

    private Double calculerTauxContreVisite(Long contreVisites, Long totalRemboursements) {
        if (totalRemboursements == null || totalRemboursements == 0) {
            return 0.0;
        }
        return (contreVisites * 100.0) / totalRemboursements;
    }

    public List<Integer> getAnneesDisponibles(String persoId) {
        List<Remboursement> remboursements = remboursementRepository.findByPersoId(persoId);

        return remboursements.stream()
                .filter(r -> r.getDatBs() != null)
                .map(r -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(r.getDatBs());
                    return cal.get(Calendar.YEAR);
                })
                .distinct()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    //--------------------------------------------------------------------------//
    // 1️⃣ Nombre total de remboursements pour un PERSO_ID
    public long getTotalRemboursements(String persoId) {
        return remboursementRepository.countByPersoId(persoId);
    }

    // 2️⃣ Nombre total pour un PERSO_ID dans une année
    public long getTotalRemboursementsByYear(String persoId, Integer annee) {
        return remboursementRepository.countByPersoIdAndYear(persoId, annee);
    }

    // 3️⃣ Nombre total remboursements conjoint (RANG = 90, 98, 99)
    public long getTotalConjointByYear(String persoId, Integer annee) {
        List<Remboursement> list = remboursementRepository.findByPersoIdAndYear(persoId, annee);

        return list.stream()
                .filter(r -> r.getRang() != null &&
                        (r.getRang().equals("90") ||
                                r.getRang().equals("98") ||
                                r.getRang().equals("99")))
                .count();
    }

    // 4️⃣ Nombre total remboursements adhérent (RANG = 0 ou 00)
    public long getTotalAdherentByYear(String persoId, Integer annee) {
        List<Remboursement> list = remboursementRepository.findByPersoIdAndYear(persoId, annee);

        return list.stream()
                .filter(r -> r.getRang() != null &&
                        (r.getRang().equals("0") || r.getRang().equals("00")))
                .count();
    }

    // 5️⃣ Nombre total remboursement enfant spécifique (RANG = 01/1, 02/2… jusqu'à 07/7)
    public long getTotalEnfantByYear(String persoId, Integer annee, Integer numEnfant) {

        String r1 = numEnfant.toString();              // ex: 1
        String r2 = numEnfant < 10 ? "0" + numEnfant : String.valueOf(numEnfant); // ex: "01"

        List<Remboursement> list = remboursementRepository.findByPersoIdAndYear(persoId, annee);

        return list.stream()
                .filter(r -> r.getRang() != null &&
                        (r.getRang().equals(r1) || r.getRang().equals(r2)))
                .count();
    }


    // 6️⃣ Statistiques des réclamations
    public Map<String, Object> getStatistiquesReclamations(String persoId, Integer annee) {
        List<Reclamation> reclamations = reclamationRepository.findByPersoIdAndYear(persoId, annee);

        long total = reclamations.size();
        long repondues = reclamations.stream()
                .filter(r -> r.getResponseRec() != null && !r.getResponseRec().isEmpty())
                .count();
        long nonRepondues = total - repondues;

        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("repondues", repondues);
        result.put("nonRepondues", nonRepondues);

        return result;
    }

    // 7️⃣ Nombre de contre-visites
    public long getStatistiquesContreVisites(String persoId, Integer annee) {
        List<Remboursement> remboursements = remboursementRepository.findByPersoIdAndYear(persoId, annee);

        return remboursements.stream()
                .filter(r -> r.getCodDoctCv() != null && !r.getCodDoctCv().isEmpty())
                .count();
    }

    // 8️⃣ Évolution des remboursements par mois
    public Map<String, Long> getEvolutionRemboursementsParMois(String persoId, Integer annee) {
        List<Remboursement> remboursements = remboursementRepository.findByPersoIdAndYear(persoId, annee);

        Map<String, Long> evolution = new LinkedHashMap<>();
        String[] mois = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};

        for (int i = 0; i < 12; i++) {
            evolution.put(mois[i], 0L);
        }

        for (Remboursement r : remboursements) {
            if (r.getDatBs() != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(r.getDatBs());
                int monthIndex = cal.get(Calendar.MONTH);

                String moisKey = mois[monthIndex];
                evolution.put(moisKey, evolution.get(moisKey) + 1);
            }
        }

        return evolution;
    }

    // 9️⃣ Statistiques par année (toutes les années)
    public Map<Integer, Long> getStatistiquesParAnnee(String persoId) {
        List<Remboursement> remboursements = remboursementRepository.findByPersoId(persoId);

        Map<Integer, Long> stats = new TreeMap<>(Collections.reverseOrder());

        for (Remboursement r : remboursements) {
            if (r.getDatBs() != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(r.getDatBs());
                int annee = cal.get(Calendar.YEAR);

                stats.put(annee, stats.getOrDefault(annee, 0L) + 1);
            }
        }

        return stats;
    }


}

