package com.smldb2.demo.services;
import com.smldb2.demo.Entity.Suggestion;
import com.smldb2.demo.repositories.SuggestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuggestionService {
    @Autowired
    private SuggestionRepository suggestionRepository;

    public List<Suggestion> getAllSuggestions() {
        return suggestionRepository.findAll();
    }

    public Optional<Suggestion> getSuggestionById(Integer id) {
        return suggestionRepository.findById(id);
    }

    public List<Suggestion> getSuggestionsByPersoId(String persoId) {
        return suggestionRepository.findByPersoId(persoId);
    }

    public List<Suggestion> getSuggestionsByExported(String exported) {
        return suggestionRepository.findByExported(exported);
    }
}
