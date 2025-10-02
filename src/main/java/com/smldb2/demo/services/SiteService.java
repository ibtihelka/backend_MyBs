package com.smldb2.demo.services;
import com.smldb2.demo.Entity.Site;
import com.smldb2.demo.repositories.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SiteService {
    @Autowired
    private SiteRepository siteRepository;

    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }

    public Optional<Site> getSiteById(String id) {
        return siteRepository.findById(id);
    }

    public List<Site> getSitesByCodeEntite(String codeEntite) {
        return siteRepository.findByCodeEntite(codeEntite);
    }

    public Optional<Site> getSiteByDescription(String description) {
        return siteRepository.findByDesSit(description);
    }
}

