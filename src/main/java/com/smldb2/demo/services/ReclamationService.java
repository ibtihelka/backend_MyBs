package com.smldb2.demo.services;

import com.smldb2.demo.DTO.ReclamationDTO;
import com.smldb2.demo.Entity.Reclamation;
import com.smldb2.demo.Entity.Remboursement;
import com.smldb2.demo.Entity.User;
import com.smldb2.demo.repositories.ReclamationRepository;
import com.smldb2.demo.repositories.RemboursementRepository;
import com.smldb2.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReclamationService {

    @Autowired
    private ReclamationRepository reclamationRepository;

    @Autowired
    private RemboursementRepository remboursementRepository;

    @Autowired
    private UserRepository userRepository;

    // 🔢 LIMITE DE RÉCLAMATIONS PAR REMBOURSEMENT
    private static final int MAX_RECLAMATIONS_PER_REMBOURSEMENT = 2;

    /**
     * Créer une nouvelle réclamation à partir d'un remboursement
     * ✅ NOUVELLE LOGIQUE : Vérification de la limite de 2 réclamations
     */
    @Transactional
    public ReclamationDTO createReclamation(ReclamationDTO reclamationDTO) {
        // Vérifier que le remboursement existe
        Optional<Remboursement> remboursementOpt = remboursementRepository.findByRefBsPhys(reclamationDTO.getRefBsPhys());
        if (remboursementOpt.isEmpty()) {
            throw new RuntimeException("Remboursement introuvable avec la référence: " + reclamationDTO.getRefBsPhys());
        }

        Remboursement remboursement = remboursementOpt.get();

        // Vérifier que le remboursement appartient bien à l'utilisateur
        if (!remboursement.getPersoId().equals(reclamationDTO.getPersoId())) {
            throw new RuntimeException("Ce remboursement n'appartient pas à cet utilisateur");
        }

        // ✅ NOUVELLE VÉRIFICATION : Compter le nombre de réclamations existantes pour ce remboursement
        long nombreReclamations = reclamationRepository.countByRefBsPhys(reclamationDTO.getRefBsPhys());

        System.out.println("📊 Nombre de réclamations existantes pour " + reclamationDTO.getRefBsPhys() + ": " + nombreReclamations);

        if (nombreReclamations >= MAX_RECLAMATIONS_PER_REMBOURSEMENT) {
            throw new RuntimeException("LIMITE_ATTEINTE:Vous avez atteint la limite de " + MAX_RECLAMATIONS_PER_REMBOURSEMENT +
                    " réclamations pour ce remboursement. Veuillez consulter votre responsable RH.");
        }

        // Vérifier que l'utilisateur existe
        Optional<User> userOpt = userRepository.findById(reclamationDTO.getPersoId());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Utilisateur introuvable avec l'ID: " + reclamationDTO.getPersoId());
        }

        // Créer la réclamation
        Reclamation reclamation = new Reclamation();
        reclamation.setRefBsPhys(reclamationDTO.getRefBsPhys());
        reclamation.setPersoId(reclamationDTO.getPersoId());
        reclamation.setTitreReclamation(reclamationDTO.getTitreReclamation());
        reclamation.setTexteReclamation(reclamationDTO.getTexteReclamation());
        reclamation.setDateCreation(new Date());
        reclamation.setExported("N");
        reclamation.setUser(userOpt.get());

        // Sauvegarder la réclamation
        Reclamation savedReclamation = reclamationRepository.save(reclamation);

        System.out.println("✅ Réclamation créée avec succès. Total pour ce BS: " + (nombreReclamations + 1) + "/" + MAX_RECLAMATIONS_PER_REMBOURSEMENT);

        // Convertir en DTO pour la réponse
        return convertToDTO(savedReclamation, remboursement);
    }

    /**
     * ✅ NOUVELLE MÉTHODE : Vérifier le nombre de réclamations pour un remboursement
     */
    public int countReclamationsByRefBsPhys(String refBsPhys) {
        return (int) reclamationRepository.countByRefBsPhys(refBsPhys);
    }

    /**
     * ✅ NOUVELLE MÉTHODE : Vérifier si l'utilisateur peut créer une nouvelle réclamation
     */
    public boolean canCreateReclamation(String refBsPhys) {
        long count = reclamationRepository.countByRefBsPhys(refBsPhys);
        return count < MAX_RECLAMATIONS_PER_REMBOURSEMENT;
    }

    /**
     * Récupérer toutes les réclamations d'un utilisateur
     */
    public List<ReclamationDTO> getReclamationsByUser(String persoId) {
        List<Reclamation> reclamations = reclamationRepository.findByPersoId(persoId);

        return reclamations.stream().map(reclamation -> {
            Optional<Remboursement> remboursement = remboursementRepository.findByRefBsPhys(reclamation.getRefBsPhys());
            return convertToDTO(reclamation, remboursement.orElse(null));
        }).collect(Collectors.toList());
    }

    /**
     * Récupérer une réclamation par son ID
     */
    public ReclamationDTO getReclamationById(Long numReclamation) {
        Optional<Reclamation> reclamationOpt = reclamationRepository.findById(numReclamation);
        if (reclamationOpt.isEmpty()) {
            throw new RuntimeException("Réclamation introuvable avec l'ID: " + numReclamation);
        }

        Reclamation reclamation = reclamationOpt.get();
        Optional<Remboursement> remboursement = remboursementRepository.findByRefBsPhys(reclamation.getRefBsPhys());

        return convertToDTO(reclamation, remboursement.orElse(null));
    }

    /**
     * Récupérer les réclamations par référence de remboursement
     */
    public List<ReclamationDTO> getReclamationsByRefBsPhys(String refBsPhys) {
        List<Reclamation> reclamations = reclamationRepository.findByRefBsPhys(refBsPhys);
        Optional<Remboursement> remboursement = remboursementRepository.findByRefBsPhys(refBsPhys);

        return reclamations.stream()
                .map(reclamation -> convertToDTO(reclamation, remboursement.orElse(null)))
                .collect(Collectors.toList());
    }

    /**
     * Vérifier si une réclamation existe déjà pour un remboursement
     */
    public boolean hasReclamation(String refBsPhys) {
        return reclamationRepository.existsByRefBsPhys(refBsPhys);
    }

    /**
     * Récupérer tous les remboursements d'un utilisateur
     */
    public List<Remboursement> getRemboursementsByUser(String persoId) {
        return remboursementRepository.findByPersoId(persoId);
    }

    /**
     * Convertir une entité Reclamation en DTO
     */
    private ReclamationDTO convertToDTO(Reclamation reclamation, Remboursement remboursement) {
        ReclamationDTO dto = new ReclamationDTO();
        dto.setNumReclamation(reclamation.getNumReclamation());
        dto.setRefBsPhys(reclamation.getRefBsPhys());
        dto.setPersoId(reclamation.getPersoId());
        dto.setTitreReclamation(reclamation.getTitreReclamation());
        dto.setTexteReclamation(reclamation.getTexteReclamation());
        dto.setResponseRec(reclamation.getResponseRec());
        dto.setExported(reclamation.getExported());
        dto.setDateCreation(reclamation.getDateCreation());

        // Ajouter les informations du remboursement si disponible
        if (remboursement != null) {
            dto.setNomPrenPrest(remboursement.getNomPrenPrest());
            dto.setDatBs(remboursement.getDatBs());
        }

        return dto;
    }

    /**
     * Supprimer une réclamation
     */
    @Transactional
    public void deleteReclamation(Long numReclamation) {
        if (!reclamationRepository.existsById(numReclamation)) {
            throw new RuntimeException("Réclamation introuvable avec l'ID: " + numReclamation);
        }
        reclamationRepository.deleteById(numReclamation);
    }

    /**
     * Mettre à jour la réponse d'une réclamation (pour l'admin)
     */
    @Transactional
    public ReclamationDTO updateReclamationResponse(Long numReclamation, String response) {
        Optional<Reclamation> reclamationOpt = reclamationRepository.findById(numReclamation);
        if (reclamationOpt.isEmpty()) {
            throw new RuntimeException("Réclamation introuvable avec l'ID: " + numReclamation);
        }

        Reclamation reclamation = reclamationOpt.get();
        reclamation.setResponseRec(response);
        Reclamation updatedReclamation = reclamationRepository.save(reclamation);

        Optional<Remboursement> remboursement = remboursementRepository.findByRefBsPhys(reclamation.getRefBsPhys());
        return convertToDTO(updatedReclamation, remboursement.orElse(null));
    }
}