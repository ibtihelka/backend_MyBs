package com.smldb2.demo.services;

import com.smldb2.demo.Entity.Rib;
import com.smldb2.demo.repositories.RibRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RibService {
    @Autowired
    private RibRepository ribRepository;

    public List<Rib> getAllRibs() {
        return ribRepository.findAll();
    }

    public Optional<Rib> getRibById(Integer id) {
        return ribRepository.findById(id);
    }

    public List<Rib> getRibsByPersoId(String persoId) {
        return ribRepository.findByPersoId(persoId);
    }

    public List<Rib> getRibsByExported(String exported) {
        return ribRepository.findByExported(exported);
    }
}
