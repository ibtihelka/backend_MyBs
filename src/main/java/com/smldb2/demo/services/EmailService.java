package com.smldb2.demo.services;

import com.smldb2.demo.DTO.DocumentInfo;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendComplementInfoEmailMultiple(
            String toEmail,
            String userPersoId,
            String userName,
            String userEmail,
            List<DocumentInfo> documents) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setFrom("ibtihelkadhraoui1@gmail.com");
            helper.setSubject("Complément d'Information - " + userName);

            // Construire le contenu HTML
            String emailContent = buildEmailContent(userPersoId, userName, userEmail, documents);
            helper.setText(emailContent, true);

            // Ajouter chaque fichier en pièce jointe
            for (DocumentInfo doc : documents) {
                ByteArrayResource resource = new ByteArrayResource(doc.getFile().getBytes());
                helper.addAttachment(doc.getFile().getOriginalFilename(), resource);
            }

            mailSender.send(message);
            System.out.println("Email envoyé avec succès à " + toEmail);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String buildEmailContent(String userPersoId, String userName, String userEmail, List<DocumentInfo> documents) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentDateTime = sdf.format(new Date());

        StringBuilder documentsHtml = new StringBuilder();
        for (DocumentInfo doc : documents) {
            documentsHtml.append("<div class='info-row'>")
                    .append("<span class='label'>Type :</span>")
                    .append("<span class='value'>").append(doc.getDocumentType()).append("</span><br>")
                    .append("<span class='label'>Fichier :</span>")
                    .append("<span class='value'>").append(doc.getFile().getOriginalFilename()).append("</span>")
                    .append("</div>");
        }

        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <meta charset='UTF-8'>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }" +
                "        .container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
                "        .header { background: linear-gradient(135deg, #c41e3a 0%, #a01729 100%); color: white; padding: 30px; text-align: center; border-radius: 8px 8px 0 0; }" +
                "        .content { background: #f9f9f9; padding: 30px; border: 1px solid #ddd; }" +
                "        .info-box { background: white; padding: 20px; margin: 15px 0; border-left: 4px solid #c41e3a; border-radius: 4px; }" +
                "        .info-row { margin: 10px 0; padding: 8px 0; border-bottom: 1px solid #eee; }" +
                "        .label { font-weight: bold; color: #c41e3a; display: inline-block; width: 120px; }" +
                "        .value { color: #555; }" +
                "        .footer { background: #333; color: white; padding: 20px; text-align: center; border-radius: 0 0 8px 8px; font-size: 12px; }" +
                "        .highlight { background: #fff9e6; padding: 15px; border: 2px solid #ffc107; border-radius: 6px; margin: 20px 0; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "    <div class='header'>" +
                "        <h1 style='margin: 0;'>📄 Complément d'Information</h1>" +
                "        <p style='margin: 10px 0 0 0; font-size: 14px;'>Nouveau(x) document(s) reçu(s)</p>" +
                "    </div>" +
                "    <div class='content'>" +

                "        <div class='info-box'>" +
                "            <h3 style='color: #c41e3a; margin-top: 0;'>Informations de l'adhérent</h3>" +
                "            <div class='info-row'><span class='label'>Nom complet :</span><span class='value'>" + userName + "</span></div>" +
                "            <div class='info-row'><span class='label'>ID Adhérent :</span><span class='value'>" + userPersoId + "</span></div>" +
                "            <div class='info-row'><span class='label'>Email :</span><span class='value'>" + userEmail + "</span></div>" +
                "        </div>" +
                "        <div class='info-box'>" +
                "            <h3 style='color: #c41e3a; margin-top: 0;'>Détails des documents</h3>" +
                documentsHtml.toString() +
                "            <div class='info-row' style='border-bottom: none;'>" +
                "                <span class='label'>Date d'envoi :</span>" +
                "                <span class='value'>" + currentDateTime + "</span>" +
                "            </div>" +
                "        </div>" +
                "        <p style='margin-top: 30px; padding: 15px; background: #e3f2fd; border-left: 4px solid #2196f3; border-radius: 4px;'>" +
                "            ℹ️ <strong>Note :</strong> Les documents sont joints à cet email." +
                "        </p>" +
                "    </div>" +
                "    <div class='footer'>" +
                "        <p style='margin: 0;'>BH Assurance - Système de gestion des compléments d'information</p>" +
                "        <p style='margin: 5px 0 0 0;'>© " + new SimpleDateFormat("yyyy").format(new Date()) + " Tous droits réservés</p>" +
                "    </div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
}
