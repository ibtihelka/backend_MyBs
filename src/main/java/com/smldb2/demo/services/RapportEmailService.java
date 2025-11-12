package com.smldb2.demo.services;

import com.smldb2.demo.Entity.Prestataire;
import com.smldb2.demo.Entity.RapportContreVisite;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RapportEmailService {

    @Autowired
    private JavaMailSender mailSender;

    private static final String ADMIN_EMAIL = "ibtihel.kadhraoui@esprit.tn";

    public boolean sendRapportEmail(RapportContreVisite rapport, Prestataire prestataire, MultipartFile image) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(ADMIN_EMAIL);
            helper.setFrom("ibtihelkadhraoui1@gmail.com");
            helper.setSubject("Nouveau Rapport de Contre Visite - " + rapport.getTypeRapport() + " - " + rapport.getRefBsPhys());

            // Construire le contenu HTML
            String emailContent = buildRapportEmailContent(rapport, prestataire);
            helper.setText(emailContent, true);

            // Ajouter l'image en pièce jointe si elle existe
            if (image != null && !image.isEmpty()) {
                try {
                    ByteArrayResource resource = new ByteArrayResource(image.getBytes());
                    helper.addAttachment(image.getOriginalFilename(), resource);
                    System.out.println("Image ajoutée en pièce jointe: " + image.getOriginalFilename());
                } catch (IOException e) {
                    System.err.println("Erreur lors de la lecture de l'image: " + e.getMessage());
                    // Continuer sans l'image plutôt que d'échouer complètement
                }
            } else {
                System.out.println("Aucune image à attacher");
            }

            mailSender.send(message);
            System.out.println("Email de rapport envoyé avec succès à " + ADMIN_EMAIL);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
            return false;
        }
    }
    private String buildRapportEmailContent(RapportContreVisite rapport, Prestataire prestataire) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentDateTime = sdf.format(new Date());

        // Construction du contenu spécifique selon le type de rapport
        StringBuilder specificContent = new StringBuilder();

        if ("DENTISTE".equalsIgnoreCase(rapport.getTypeRapport())) {
            specificContent.append("<div class='info-box'>")
                    .append("<h3 style='color: #c41e3a; margin-top: 0;'>Informations Dentaires</h3>")
                    .append("<div class='info-row'>")
                    .append("<span class='label'>Lignes dentaires :</span>")
                    .append("<span class='value'>").append(rapport.getLignesDentaire() != null ? rapport.getLignesDentaire() : "Non spécifié").append("</span>")
                    .append("</div>")
                    .append("</div>");
        } else if ("OPTICIEN".equalsIgnoreCase(rapport.getTypeRapport())) {
            specificContent.append("<div class='info-box'>")
                    .append("<h3 style='color: #c41e3a; margin-top: 0;'>Informations Optiques</h3>")
                    .append("<div class='info-row'>")
                    .append("<span class='label'>Acuité visuelle OD :</span>")
                    .append("<span class='value'>").append(rapport.getAcuiteVisuelleOD() != null ? rapport.getAcuiteVisuelleOD() : "Non spécifié").append("</span>")
                    .append("</div>")
                    .append("<div class='info-row'>")
                    .append("<span class='label'>Acuité visuelle OG :</span>")
                    .append("<span class='value'>").append(rapport.getAcuiteVisuelleOG() != null ? rapport.getAcuiteVisuelleOG() : "Non spécifié").append("</span>")
                    .append("</div>")
                    .append("<div class='info-row'>")
                    .append("<span class='label'>Prix monture :</span>")
                    .append("<span class='value'>").append(rapport.getPrixMonture() != null ? rapport.getPrixMonture() + " DT" : "Non spécifié").append("</span>")
                    .append("</div>")
                    .append("<div class='info-row'>")
                    .append("<span class='label'>Nature des verres :</span>")
                    .append("<span class='value'>").append(rapport.getNatureVerres() != null ? rapport.getNatureVerres() : "Non spécifié").append("</span>")
                    .append("</div>")
                    .append("<div class='info-row'>")
                    .append("<span class='label'>Prix des verres :</span>")
                    .append("<span class='value'>").append(rapport.getPrixVerres() != null ? rapport.getPrixVerres() + " DT" : "Non spécifié").append("</span>")
                    .append("</div>")
                    .append("</div>");
        }

        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <meta charset='UTF-8'>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }" +
                "        .container { max-width: 700px; margin: 0 auto; padding: 20px; }" +
                "        .header { background: linear-gradient(135deg, #c41e3a 0%, #a01729 100%); color: white; padding: 30px; text-align: center; border-radius: 8px 8px 0 0; }" +
                "        .content { background: #f9f9f9; padding: 30px; border: 1px solid #ddd; }" +
                "        .info-box { background: white; padding: 20px; margin: 15px 0; border-left: 4px solid #c41e3a; border-radius: 4px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }" +
                "        .info-row { margin: 10px 0; padding: 8px 0; border-bottom: 1px solid #eee; }" +
                "        .info-row:last-child { border-bottom: none; }" +
                "        .label { font-weight: bold; color: #c41e3a; display: inline-block; width: 180px; }" +
                "        .value { color: #555; }" +
                "        .footer { background: #333; color: white; padding: 20px; text-align: center; border-radius: 0 0 8px 8px; font-size: 12px; }" +
                "        .badge { display: inline-block; padding: 5px 12px; border-radius: 20px; font-size: 12px; font-weight: bold; }" +
                "        .badge-dentiste { background: #ffc107; color: #000; }" +
                "        .badge-opticien { background: #2196f3; color: white; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "    <div class='header'>" +
                "        <h1 style='margin: 0;'>🏥 Rapport de Contre Visite</h1>" +
                "        <p style='margin: 10px 0 0 0; font-size: 14px;'>Nouveau rapport soumis</p>" +
                "    </div>" +
                "    <div class='content'>" +

                "        <div class='info-box'>" +
                "            <h3 style='color: #c41e3a; margin-top: 0;'>Informations du Rapport</h3>" +
                "            <div class='info-row'>" +
                "                <span class='label'>Type de rapport :</span>" +
                "                <span class='badge " + ("DENTISTE".equalsIgnoreCase(rapport.getTypeRapport()) ? "badge-dentiste" : "badge-opticien") + "'>" + rapport.getTypeRapport() + "</span>" +
                "            </div>" +
                "            <div class='info-row'><span class='label'>N° Remboursement :</span><span class='value'>" + rapport.getRefBsPhys() + "</span></div>" +
                "            <div class='info-row'><span class='label'>Date du rapport :</span><span class='value'>" + currentDateTime + "</span></div>" +
                "        </div>" +

                "        <div class='info-box'>" +
                "            <h3 style='color: #c41e3a; margin-top: 0;'>Prestataire</h3>" +
                "            <div class='info-row'><span class='label'>ID Prestataire :</span><span class='value'>" + prestataire.getPersoId() + "</span></div>" +
                "            <div class='info-row'><span class='label'>Nom :</span><span class='value'>" + (prestataire.getNom() != null ? prestataire.getAdresse() : "Non spécifié") + "</span></div>" +
                "            <div class='info-row'><span class='label'>Rôle :</span><span class='value'>" + prestataire.getRole() + "</span></div>" +
                "        </div>" +

                "        <div class='info-box'>" +
                "            <h3 style='color: #c41e3a; margin-top: 0;'>Bénéficiaire</h3>" +
                "            <div class='info-row'><span class='label'>ID Bénéficiaire :</span><span class='value'>" + rapport.getBeneficiaireId() + "</span></div>" +
                "            <div class='info-row'><span class='label'>Nom :</span><span class='value'>" + rapport.getBeneficiaireNom() + "</span></div>" +
                "            <div class='info-row'><span class='label'>Type :</span><span class='value'>" + rapport.getTypeBeneficiaire() + "</span></div>" +
                "        </div>" +

                specificContent.toString() +

                "        <div class='info-box'>" +
                "            <h3 style='color: #c41e3a; margin-top: 0;'>Observation</h3>" +
                "            <p style='margin: 0; color: #555;'>" + (rapport.getObservation() != null && !rapport.getObservation().isEmpty() ? rapport.getObservation() : "Aucune observation") + "</p>" +
                "        </div>" +

                "        <p style='margin-top: 30px; padding: 15px; background: #e3f2fd; border-left: 4px solid #2196f3; border-radius: 4px;'>" +
                "            ℹ️ <strong>Note :</strong> L'image du rapport est jointe à cet email." +
                "        </p>" +
                "    </div>" +
                "    <div class='footer'>" +
                "        <p style='margin: 0;'>BH Assurance - Système de gestion des rapports de contre visite</p>" +
                "        <p style='margin: 5px 0 0 0;'>© " + new SimpleDateFormat("yyyy").format(new Date()) + " Tous droits réservés</p>" +
                "    </div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
}