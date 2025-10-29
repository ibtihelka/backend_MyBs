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

    // Créer un rapport avec image et envoi par email
    public Map<String, Object> creerRapportAvecImage(String rapportJson, MultipartFile image) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Parser le JSON en objet RapportContreVisite
            RapportContreVisite rapport = objectMapper.readValue(rapportJson, RapportContreVisite.class);

            // Vérifier que le prestataire existe
            Optional<Prestataire> prestataire = prestataireRepository.findById(rapport.getPrestataireId());
            if (!prestataire.isPresent()) {
                response.put("success", false);
                response.put("message", "Prestataire introuvable");
                return response;
            }

            // Vérifier que le bénéficiaire existe et le nom correspond
            Map<String, Object> validationBenef = validerBeneficiaire(
                    rapport.getBeneficiaireId(),
                    rapport.getBeneficiaireNom()
            );

            if (!(boolean) validationBenef.get("valid")) {
                response.put("success", false);
                response.put("message", validationBenef.get("message"));
                return response;
            }

            // Récupérer le type de bénéficiaire validé
            rapport.setTypeBeneficiaire((String) validationBenef.get("type"));

            // Vérifier que le type de rapport correspond au rôle du prestataire
            String rolePrestataire = prestataire.get().getRole();
            String typeRapport = rapport.getTypeRapport();

            if (!rolePrestataire.equalsIgnoreCase(typeRapport)) {
                response.put("success", false);
                response.put("message", "Un prestataire " + rolePrestataire + " ne peut créer que des rapports de type " + rolePrestataire);
                return response;
            }

            // Validation supplémentaire selon le type
            if ("DENTISTE".equalsIgnoreCase(typeRapport)) {
                if (rapport.getLignesDentaire() == null || rapport.getLignesDentaire().isEmpty()) {
                    response.put("success", false);
                    response.put("message", "Les lignes dentaires sont obligatoires pour un rapport DENTISTE");
                    return response;
                }
            } else if ("OPTICIEN".equalsIgnoreCase(typeRapport)) {
                if (rapport.getPrixMonture() == null) {
                    response.put("success", false);
                    response.put("message", "Le prix de la monture est obligatoire pour un rapport OPTICIEN");
                    return response;
                }
            }

            // Sauvegarder le rapport
            RapportContreVisite saved = rapportRepository.save(rapport);

            // Envoyer l'email avec l'image si elle existe
            if (image != null && !image.isEmpty()) {
                boolean emailSent = rapportEmailService.sendRapportEmail(saved, prestataire.get(), image);

                if (!emailSent) {
                    System.out.println("Avertissement : Le rapport a été créé mais l'email n'a pas pu être envoyé");
                }
            }

            response.put("success", true);
            response.put("message", "Rapport créé avec succès" + (image != null ? " et email envoyé" : ""));
            response.put("rapport", saved);
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Erreur lors de la création du rapport: " + e.getMessage());
            return response;
        }
    }
    // Créer un rapport sans image (méthode existante pour compatibilité)
    public Map<String, Object> creerRapport(RapportContreVisite rapport) {
        // CORRECTION : Convertir l'objet en JSON au lieu d'utiliser toString()
        try {
            String rapportJson = objectMapper.writeValueAsString(rapport);
            return creerRapportAvecImage(rapportJson, null);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Erreur lors de la conversion du rapport en JSON: " + e.getMessage());
            return response;
        }
    }

    // Valider le bénéficiaire (User ou Famille)
    private Map<String, Object> validerBeneficiaire(String beneficiaireId, String beneficiaireNom) {
        Map<String, Object> result = new HashMap<>();

        // D'abord chercher dans la table User
        Optional<User> user = userRepository.findById(beneficiaireId);
        if (user.isPresent()) {
            // Vérifier si le nom correspond au user lui-même
            if (user.get().getPersoName().equalsIgnoreCase(beneficiaireNom)) {
                result.put("valid", true);
                result.put("type", "USER");
                result.put("message", "Bénéficiaire valide");
                return result;
            }

            // Sinon, vérifier dans sa famille
            List<Famille> familles = familleRepository.findByPersoId(beneficiaireId);
            for (Famille f : familles) {
                if (f.getPrenomPrestataire().equalsIgnoreCase(beneficiaireNom)) {
                    result.put("valid", true);
                    result.put("type", "FAMILLE");
                    result.put("message", "Bénéficiaire valide");
                    return result;
                }
            }

            // Le user existe mais le nom ne correspond ni au user ni à sa famille
            result.put("valid", false);
            result.put("message", "Le nom du bénéficiaire ne correspond pas à l'ID fourni");
            return result;
        }

        // Le bénéficiaire n'existe pas
        result.put("valid", false);
        result.put("message", "Bénéficiaire introuvable avec l'ID: " + beneficiaireId);
        return result;
    }

    // Récupérer les bénéficiaires (User + Famille) pour un remboursement
    public Map<String, Object> getBeneficiaires(String refBsPhys) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, String>> beneficiaires = new ArrayList<>();

        // Trouver le remboursement
        Optional<Remboursement> remb = remboursementRepository.findById(refBsPhys);
        if (remb.isPresent()) {
            String persoId = remb.get().getPersoId();

            // Ajouter le user principal
            Optional<User> user = userRepository.findById(persoId);
            if (user.isPresent()) {
                Map<String, String> ben = new HashMap<>();
                ben.put("id", user.get().getPersoId());
                ben.put("nom", user.get().getPersoName());
                ben.put("type", "USER");
                beneficiaires.add(ben);
            }

            // Ajouter les membres de la famille
            List<Famille> familles = familleRepository.findByPersoId(persoId);
            for (Famille f : familles) {
                Map<String, String> ben = new HashMap<>();
                ben.put("id", f.getPrenomPrestataire());
                ben.put("nom", f.getPrenomPrestataire());
                ben.put("type", "FAMILLE");
                beneficiaires.add(ben);
            }
        }

        result.put("beneficiaires", beneficiaires);
        return result;
    }

    // Récupérer tous les rapports d'un prestataire
    public List<RapportContreVisite> getRapportsParPrestataire(String prestataireId) {
        return rapportRepository.findByPrestataireId(prestataireId);
    }

    // Récupérer tous les remboursements disponibles
    public List<Remboursement> getAllRemboursements() {
        return remboursementRepository.findAll();
    }
}