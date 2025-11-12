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
            helper.setText(emailContent, true); // IMPORTANT: true pour HTML

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
                    .append("<span class='value'>").append(escapeHtml(doc.getDocumentType())).append("</span><br>")
                    .append("<span class='label'>Fichier :</span>")
                    .append("<span class='value'>").append(escapeHtml(doc.getFile().getOriginalFilename())).append("</span>")
                    .append("</div>");
        }

        return "<!DOCTYPE html>" +
                "<html lang='fr'>" +
                "<head>" +
                "    <meta charset='UTF-8'>" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>" +
                "    <title>Complément d'Information</title>" +
                "    <style>" +
                "        * { margin: 0; padding: 0; box-sizing: border-box; }" +
                "        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; background-color: #f4f4f4; }" +
                "        .container { max-width: 600px; margin: 20px auto; background-color: #ffffff; }" +
                "        .header { background: linear-gradient(135deg, #c41e3a 0%, #a01729 100%); color: white; padding: 30px; text-align: center; border-radius: 8px 8px 0 0; }" +
                "        .header h1 { margin: 0; font-size: 24px; }" +
                "        .header p { margin: 10px 0 0 0; font-size: 14px; }" +
                "        .content { background: #f9f9f9; padding: 30px; border: 1px solid #ddd; }" +
                "        .info-box { background: white; padding: 20px; margin: 15px 0; border-left: 4px solid #c41e3a; border-radius: 4px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }" +
                "        .info-box h3 { color: #c41e3a; margin-top: 0; margin-bottom: 15px; }" +
                "        .info-row { margin: 10px 0; padding: 8px 0; border-bottom: 1px solid #eee; }" +
                "        .info-row:last-child { border-bottom: none; }" +
                "        .label { font-weight: bold; color: #c41e3a; display: inline-block; width: 120px; }" +
                "        .value { color: #555; }" +
                "        .note { margin-top: 30px; padding: 15px; background: #e3f2fd; border-left: 4px solid #2196f3; border-radius: 4px; }" +
                "        .footer { background: #333; color: white; padding: 20px; text-align: center; border-radius: 0 0 8px 8px; font-size: 12px; }" +
                "        .footer p { margin: 5px 0; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "    <div class='header'>" +
                "        <h1>📄 Complément d'Information</h1>" +
                "        <p>Nouveau(x) document(s) reçu(s)</p>" +
                "    </div>" +
                "    <div class='content'>" +
                "        <div class='info-box'>" +
                "            <h3>Informations de l'adhérent</h3>" +
                "            <div class='info-row'>" +
                "                <span class='label'>Nom complet :</span>" +
                "                <span class='value'>" + escapeHtml(userName) + "</span>" +
                "            </div>" +
                "            <div class='info-row'>" +
                "                <span class='label'>ID Adhérent :</span>" +
                "                <span class='value'>" + escapeHtml(userPersoId) + "</span>" +
                "            </div>" +
                "            <div class='info-row'>" +
                "                <span class='label'>Email :</span>" +
                "                <span class='value'>" + escapeHtml(userEmail) + "</span>" +
                "            </div>" +
                "        </div>" +
                "        <div class='info-box'>" +
                "            <h3>Détails des documents</h3>" +
                documentsHtml.toString() +
                "            <div class='info-row'>" +
                "                <span class='label'>Date d'envoi :</span>" +
                "                <span class='value'>" + currentDateTime + "</span>" +
                "            </div>" +
                "        </div>" +
                "        <div class='note'>" +
                "            ℹ️ <strong>Note :</strong> Les documents sont joints à cet email." +
                "        </div>" +
                "    </div>" +
                "    <div class='footer'>" +
                "        <p>BH Assurance - Système de gestion des compléments d'information</p>" +
                "        <p>© " + new SimpleDateFormat("yyyy").format(new Date()) + " Tous droits réservés</p>" +
                "    </div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    // Méthode pour échapper les caractères HTML
    private String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }
}