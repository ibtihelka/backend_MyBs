package com.smldb2.demo.services;


import com.smldb2.demo.DTO.StatMedecinActeDTO;

import com.smldb2.demo.repositories.StatActDateRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
public class StatistiqueService {

    private static final Logger logger = LoggerFactory.getLogger(StatistiqueService.class);
    private final StatActDateRepository statActDateRepository;

    public StatistiqueService(StatActDateRepository statActDateRepository) {
        this.statActDateRepository = statActDateRepository;
    }

    /**
     * Récupère le médecin avec le plus d'actes pour chaque type d'acte d'une société
     */
    public List<StatMedecinActeDTO> getTopMedecinParActe(String codeSociete) {
        logger.info("Récupération des statistiques pour la société: {}", codeSociete);

        List<Object[]> results = statActDateRepository.findTopMedecinParActeBySocieteNative(codeSociete);

        logger.info("Nombre de résultats trouvés: {}", results.size());

        Map<String, StatMedecinActeDTO> topParActe = new LinkedHashMap<>();

        for (Object[] row : results) {
            String acte = (String) row[0];
            Long nombreActes = ((Number) row[1]).longValue();
            String nomMedecin = (String) row[2];
            String villeMedecin = (String) row[3];
            BigDecimal totalDepense = (BigDecimal) row[4];
            BigDecimal totalRembourse = (BigDecimal) row[5];

            logger.debug("Acte: {}, Médecin: {}, Ville: {}, Nombre: {}",
                    acte, nomMedecin, villeMedecin, nombreActes);

            // Prendre seulement le premier médecin pour chaque type d'acte
            if (!topParActe.containsKey(acte)) {
                StatMedecinActeDTO dto = new StatMedecinActeDTO(
                        acte, nombreActes, nomMedecin, villeMedecin,
                        totalDepense, totalRembourse
                );
                topParActe.put(acte, dto);
            }
        }

        logger.info("Nombre de types d'actes distincts: {}", topParActe.size());

        return new ArrayList<>(topParActe.values());
    }

    /**
     * Récupère tous les codes société disponibles
     */
    public List<String> getAllCodeSociete() {
        logger.info("Récupération de tous les codes société");
        return statActDateRepository.findAllCodeSociete();
    }
}