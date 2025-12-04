package com.smldb2.demo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smldb2.demo.Entity.*;
import com.smldb2.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
@Service
public class RapportContreVisiteService {

    @Autowired
    private RapportContreVisiteRepository rapportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FamilleRepository familleRepository;

    @Autowired
    private RemboursementRepository remboursementRepository;

    @Autowired
    private PrestataireRepository prestataireRepository;

    @Autowired
    private RapportEmailService rapportEmailService;

    @Autowired
    private ObjectMapper objectMapper;

    // Créer un rapport en sélectionnant adhérent + remboursement + bénéficiaire
    public Map<String, Object> creerRapportPourRemboursement(String prestataireId, String refBsPhys, String beneficiaireNom, MultipartFile image, RapportContreVisite rapport) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Prestataire> prestataireOpt = prestataireRepository.findById(prestataireId);
            if (!prestataireOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Prestataire introuvable");
                return response;
            }
            Prestataire prestataire = prestataireOpt.get();

            Optional<Remboursement> rembOpt = remboursementRepository.findById(refBsPhys);
            if (!rembOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Bulletin de soins introuvable");
                return response;
            }
            Remboursement remboursement = rembOpt.get();
            String persoId = remboursement.getPersoId();

            Optional<User> userOpt = userRepository.findById(persoId);
            if (!userOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Adhérent introuvable");
                return response;
            }
            User user = userOpt.get();

            // Déterminer le type du bénéficiaire
            String typeBeneficiaire;
            String nomBeneficiaire;

            if (beneficiaireNom.equalsIgnoreCase(user.getPersoName())) {
                typeBeneficiaire = "ADHERENT";
                nomBeneficiaire = user.getPersoName();
            } else {
                List<Famille> familles = familleRepository.findByPersoId(persoId);
                Famille famille = familles.stream()
                        .filter(f -> f.getPrenomPrestataire().equalsIgnoreCase(beneficiaireNom))
                        .findFirst()
                        .orElse(null);
                if (famille != null) {
                    typeBeneficiaire = famille.getTypPrestataire().name();
                    nomBeneficiaire = famille.getPrenomPrestataire();
                } else {
                    response.put("success", false);
                    response.put("message", "Bénéficiaire non trouvé dans la famille de l'adhérent");
                    return response;
                }
            }

            // Remplir le rapport
            rapport.setPrestataireId(prestataireId);
            rapport.setRefBsPhys(refBsPhys);
            rapport.setBeneficiaireId(persoId);
            rapport.setBeneficiaireNom(nomBeneficiaire);
            rapport.setTypeBeneficiaire(typeBeneficiaire);

            // Vérification du type de rapport
            if (!prestataire.getRole().equalsIgnoreCase(rapport.getTypeRapport())) {
                response.put("success", false);
                response.put("message", "Le type de rapport doit correspondre au rôle du prestataire");
                return response;
            }

            RapportContreVisite saved = rapportRepository.save(rapport);

            if (image != null && !image.isEmpty()) {
                rapportEmailService.sendRapportEmail(saved, prestataire, image);
            }

            response.put("success", true);
            response.put("message", "Rapport créé avec succès");
            response.put("rapport", saved);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Erreur: " + e.getMessage());
        }

        return response;
    }

    // Récupérer tous les rapports d'un prestataire avec type bénéficiaire lisible
    public List<Map<String, String>> getRapportsAvecType(String prestataireId) {
        List<RapportContreVisite> rapports = rapportRepository.findByPrestataireId(prestataireId);
        List<Map<String, String>> result = new ArrayList<>();
        for (RapportContreVisite r : rapports) {
            Map<String, String> map = new HashMap<>();
            map.put("id", r.getId().toString());
            map.put("refBsPhys", r.getRefBsPhys());
            map.put("beneficiaireNom", r.getBeneficiaireNom());
            map.put("typeBeneficiaire", r.getTypeBeneficiaire().equalsIgnoreCase("ADHERENT") ? "Adhérent" : r.getTypeBeneficiaire());
            result.add(map);
        }
        return result;
    }

    public List<Remboursement> getRemboursementsByUser(String persoId) {
        return remboursementRepository.findByPersoId(persoId);
    }

    public List<User> getAllAdherents() {
        return userRepository.findAll();
    }

    public List<Famille> getFamilleByUser(String persoId) {
        return familleRepository.findByPersoId(persoId);
    }

    public User getAdherentByMatricule(String matricule) {
        return userRepository.findByCin(matricule).orElse(null);
    }
    public Map<String, Object> creerRapportParMatricule(
            String matriculeAdherent, String refBsPhys, String prestataireId,
            MultipartFile image, RapportContreVisite rapport) {

        Map<String, Object> response = new HashMap<>();
        try {
            Optional<User> userOpt = userRepository.findByCin(matriculeAdherent);
            if (!userOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Aucun adhérent trouvé avec cette matricule (CIN)");
                return response;
            }
            User user = userOpt.get();

            Optional<Remboursement> rembOpt = remboursementRepository.findById(refBsPhys);
            if (!rembOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Remboursement introuvable");
                return response;
            }

            Optional<Prestataire> prestataireOpt = prestataireRepository.findById(prestataireId);
            if (!prestataireOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Prestataire introuvable");
                return response;
            }

            Prestataire prestataire = prestataireOpt.get();

            // Remplissage du rapport
            rapport.setPrestataireId(prestataireId);
            rapport.setBeneficiaireId(user.getPersoId());
            rapport.setBeneficiaireNom(user.getPersoName());
            rapport.setTypeBeneficiaire("ADHERENT");
            rapport.setRefBsPhys(refBsPhys);
            rapport.setDateRapport(new Date());

            if (!prestataire.getRole().equalsIgnoreCase(rapport.getTypeRapport())) {
                response.put("success", false);
                response.put("message", "Le type de rapport ne correspond pas au rôle du prestataire");
                return response;
            }

            RapportContreVisite saved = rapportRepository.save(rapport);

            // Envoi d’un mail s’il y a une image jointe
            if (image != null && !image.isEmpty()) {
                rapportEmailService.sendRapportEmail(saved, prestataire, image);
            }

            response.put("success", true);
            response.put("message", "Rapport créé avec succès");
            response.put("rapport", saved);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Erreur : " + e.getMessage());
        }
        return response;
    }



    /**
     * Récupère le bénéficiaire d’un remboursement avec son type.
     */
    public Map<String, String> getBeneficiaireParRefBsPhys(String refBsPhys) throws Exception {
        // 1️⃣ Récupérer le remboursement
        Remboursement remboursement = remboursementRepository.findById(refBsPhys)
                .orElseThrow(() -> new Exception("Remboursement introuvable"));

        String persoIdRemb = remboursement.getPersoId();
        String nomPrenRemb = remboursement.getNomPrenPrest();

        // 2️⃣ Vérifier que l’adhérent existe
        User user = userRepository.findById(persoIdRemb)
                .orElseThrow(() -> new Exception("Adhérent introuvable"));

        // 3️⃣ Récupérer les membres de la famille de cet adhérent
        List<Famille> familles = familleRepository.findByPersoId(persoIdRemb);

        // 4️⃣ Déterminer le type du bénéficiaire
        String typeBeneficiaire = "ADHERENT"; // valeur par défaut (si ce n’est pas un membre de famille)

        for (Famille f : familles) {
            if (f.getPersoId() != null && f.getPersoId().equalsIgnoreCase(persoIdRemb)
                    && f.getPrenomPrestataire() != null
                    && f.getPrenomPrestataire().trim().equalsIgnoreCase(nomPrenRemb.trim())) {

                if (f.getTypPrestataire() != null) {
                    typeBeneficiaire = f.getTypPrestataire().name(); // ex: "CONJOINT", "ENFANT"
                }
                break;
            }
        }

        // 5️⃣ Construire la réponse
        Map<String, String> beneficiaireMap = new HashMap<>();
        beneficiaireMap.put("nom", nomPrenRemb);
        beneficiaireMap.put("type", typeBeneficiaire);

        return beneficiaireMap;
    }

    public List<Map<String, Object>> getRapportsByBeneficiaireId(String beneficiaireId) {
        List<RapportContreVisite> rapports = rapportRepository.findByBeneficiaireId(beneficiaireId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (RapportContreVisite rapport : rapports) {
            Map<String, Object> map = new HashMap<>();

            // 🧩 Informations du rapport
            map.put("beneficiaireNom", rapport.getBeneficiaireNom());
            map.put("dateRapport", rapport.getDateRapport());
            map.put("typeRapport", rapport.getTypeRapport());
            map.put("refBsPhys", rapport.getRefBsPhys());

            // 🧩 Informations du prestataire
            Optional<Prestataire> prestataireOpt = prestataireRepository.findById(rapport.getPrestataireId());
            if (prestataireOpt.isPresent()) {
                Prestataire p = prestataireOpt.get();
                map.put("prestataireNom", p.getNom());
                map.put("prestataireContact", p.getContact());
                map.put("prestataireAdresse", p.getAdresse());
                map.put("prestataireMatriculeFiscale", p.getCodDoc());
            } else {
                // Si le prestataire est introuvable
                map.put("prestataireNom", "Inconnu");
                map.put("prestataireContact", "");
                map.put("prestataireAdresse", "");
                map.put("prestataireMatriculeFiscale", "");
            }

            result.add(map);
        }

        return result;
    }

    public List<Map<String, Object>> getRapportsDetailsByPrestataire(String prestataireId) {
        List<RapportContreVisite> rapports = rapportRepository.findByPrestataireId(prestataireId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (RapportContreVisite rapport : rapports) {
            Map<String, Object> map = new HashMap<>();
            try {
                // Appel de ta fonction existante pour récupérer le type du bénéficiaire
                Map<String, String> beneficiaireInfo = getBeneficiaireParRefBsPhys(rapport.getRefBsPhys());

                map.put("beneficiaireNom", rapport.getBeneficiaireNom());
                map.put("dateRapport", rapport.getDateRapport());
                map.put("refBsPhys", rapport.getRefBsPhys());
                map.put("observation", rapport.getObservation());
                map.put("type", beneficiaireInfo.get("type")); // Type obtenu via getBeneficiaireParRefBsPhys

            } catch (Exception e) {
                // Si erreur dans la récupération du bénéficiaire
                map.put("beneficiaireNom", rapport.getBeneficiaireNom());
                map.put("dateRapport", rapport.getDateRapport());
                map.put("refBsPhys", rapport.getRefBsPhys());
                map.put("observation", rapport.getObservation());
                map.put("type", "Inconnu");
            }

            result.add(map);
        }

        return result;
    }





}
