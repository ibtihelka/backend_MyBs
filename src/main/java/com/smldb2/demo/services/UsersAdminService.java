package com.smldb2.demo.services;import com.smldb2.demo.repositories.UsersAdminRepository;
import com.smldb2.demo.Entity.UsersAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;

@Service
public class UsersAdminService {
    @Autowired
    private UsersAdminRepository usersAdminRepository;

    public List<UsersAdmin> getAllUsersAdmin() {
        return usersAdminRepository.findAll();
    }

    public Optional<UsersAdmin> getUserAdminById(String id) {
        return usersAdminRepository.findById(id);
    }

    public Optional<UsersAdmin> getUserAdminByName(String name) {
        return usersAdminRepository.findByPersoName(name);
    }

    // Méthode pour hacher en MD5
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

    public Optional<UsersAdmin> login(String persoId, String password) {
        String hashedPassword = md5(password);
        return usersAdminRepository.findByPersoIdAndPersoPassed(persoId, hashedPassword);
    }
}
