package com.smldb2.demo.services;

import com.smldb2.demo.Entity.UsersSociete;
import com.smldb2.demo.repositories.UsersSocieteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;

@Service
public class UsersSocieteService {

    @Autowired
    private UsersSocieteRepository usersSocieteRepository;

    public List<UsersSociete> getAllUsersSociete() {
        return usersSocieteRepository.findAll();
    }

    public Optional<UsersSociete> getUserSocieteById(String id) {
        return usersSocieteRepository.findById(id);
    }

    public Optional<UsersSociete> getUserSocieteByName(String name) {
        return usersSocieteRepository.findByPersoName(name);
    }

    // Méthode pour hacher le mot de passe en MD5
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

    public Optional<UsersSociete> login(String persoId, String password) {
        String hashedPassword = md5(password);
        return usersSocieteRepository.findByPersoIdAndPersoPassed(persoId, hashedPassword);
    }
}
