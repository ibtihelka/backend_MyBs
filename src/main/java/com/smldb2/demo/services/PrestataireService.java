package com.smldb2.demo.services;

import com.smldb2.demo.Entity.Prestataire;
import com.smldb2.demo.repositories.PrestataireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrestataireService {

    @Autowired
    private PrestataireRepository prestataireRepository;

    private String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02X", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Prestataire creerPrestataire(Prestataire prestataire) {
        if (prestataire.getPersoPassed() != null) {
            prestataire.setPersoPassed(md5(prestataire.getPersoPassed()));
        }
        return prestataireRepository.save(prestataire);
    }

    public List<Prestataire> getAllPrestataires() {
        return prestataireRepository.findAll();
    }

    public Optional<Prestataire> getPrestataireById(String persoId) {
        return prestataireRepository.findById(persoId);
    }

    public List<Prestataire> getPrestatairesByRole(String role) {
        return prestataireRepository.findAll().stream()
                .filter(p -> role.equalsIgnoreCase(p.getRole()))
                .collect(Collectors.toList());
    }

    public Prestataire updatePrestataire(String persoId, Prestataire prestataire) {
        Optional<Prestataire> existing = prestataireRepository.findById(persoId);
        if (existing.isPresent()) {
            Prestataire p = existing.get();
            if (prestataire.getNom() != null) p.setNom(prestataire.getNom());
            if (prestataire.getRole() != null) p.setRole(prestataire.getRole());
            if (prestataire.getEmail() != null) p.setEmail(prestataire.getEmail());
            if (prestataire.getContact() != null) p.setContact(prestataire.getContact());
            if (prestataire.getAdresse() != null) p.setAdresse(prestataire.getAdresse());
            if (prestataire.getSexe() != null) p.setSexe(prestataire.getSexe());
            if (prestataire.getMatriculeFiscale() != null) p.setMatriculeFiscale(prestataire.getMatriculeFiscale());
            if (prestataire.getPersoPassed() != null) {
                p.setPersoPassed(md5(prestataire.getPersoPassed()));
            }
            return prestataireRepository.save(p);
        }
        return null;
    }

    public boolean deletePrestataire(String persoId) {
        if (prestataireRepository.existsById(persoId)) {
            prestataireRepository.deleteById(persoId);
            return true;
        }
        return false;
    }
}
