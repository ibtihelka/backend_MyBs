package com.smldb2.demo.controllers;

import com.smldb2.demo.DTO.DocumentInfo;
import com.smldb2.demo.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/complement-info")
@CrossOrigin(origins = "**")
public class ComplementInfoController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email-multiple")
    public ResponseEntity<?> sendComplementInfoMultiple(
            @RequestParam("userPersoId") String userPersoId,
            @RequestParam("userName") String userName,
            @RequestParam("userEmail") String userEmail,
            @RequestParam("documentTypes") String[] documentTypes,
            @RequestParam("files") MultipartFile[] files) {

        try {
            if (files == null || files.length == 0 || documentTypes.length != files.length) {
                return ResponseEntity.badRequest().body(Map.of("error", "Chaque fichier doit avoir un type associé"));
            }

            List<DocumentInfo> documents = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                // Vérifier type et taille
                if (!files[i].getContentType().equals("application/pdf")) {
                    return ResponseEntity.badRequest()
                            .body(Map.of("error", "Seuls les fichiers PDF sont acceptés"));
                }
                if (files[i].getSize() > 5 * 1024 * 1024) {
                    return ResponseEntity.badRequest()
                            .body(Map.of("error", "La taille du fichier " + files[i].getOriginalFilename() + " dépasse 5MB"));
                }

                documents.add(new DocumentInfo(documentTypes[i], files[i]));
            }

            boolean sent = emailService.sendComplementInfoEmailMultiple(
                    "ibtihelkadhraoui1@gmail.com",
                    userPersoId,
                    userName,
                    userEmail,
                    documents
            );

            if (sent) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Documents envoyés avec succès");
                response.put("filesCount", files.length);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "Erreur lors de l'envoi de l'email"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erreur serveur: " + e.getMessage()));
        }
    }
}
