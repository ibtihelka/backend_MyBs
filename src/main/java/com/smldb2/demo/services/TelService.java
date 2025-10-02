package com.smldb2.demo.services;



import com.smldb2.demo.Entity.Tel;
import com.smldb2.demo.repositories.TelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelService {
    @Autowired
    private TelRepository telRepository;

    public List<Tel> getAllTels() {
        return telRepository.findAll();
    }

    public Optional<Tel> getTelById(Integer id) {
        return telRepository.findById(id);
    }

    public List<Tel> getTelsByPersoId(String persoId) {
        return telRepository.findByPersoId(persoId);
    }

    public List<Tel> getTelsByExported(String exported) {
        return telRepository.findByExported(exported);
    }
}
