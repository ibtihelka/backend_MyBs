package com.smldb2.demo.services;


import com.smldb2.demo.DTO.UnifiedLoginResponse;
import com.smldb2.demo.repositories.UserRepository;
import com.smldb2.demo.repositories.UsersAdminRepository;
import com.smldb2.demo.Entity.User;
import com.smldb2.demo.Entity.UsersAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsersAdminRepository usersAdminRepository;

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

    public UnifiedLoginResponse login(String persoId, String password) {
        String hashedPassword = md5(password);

        // D'abord, chercher dans la table users
        Optional<User> user = userRepository.findByPersoIdAndPersoPassed(persoId, hashedPassword);
        if (user.isPresent()) {
            return new UnifiedLoginResponse(
                    "Login successful",
                    true,
                    "USER",
                    user.get(),
                    null
            );
        }

        // Si non trouvé, chercher dans la table usersadmin
        Optional<UsersAdmin> admin = usersAdminRepository.findByPersoIdAndPersoPassed(persoId, hashedPassword);
        if (admin.isPresent()) {
            return new UnifiedLoginResponse(
                    "Login successful",
                    true,
                    "ADMIN",
                    null,
                    admin.get()
            );
        }

        // Si aucun utilisateur trouvé
        return new UnifiedLoginResponse(
                "Invalid credentials",
                false,
                null,
                null,
                null
        );
    }
}
