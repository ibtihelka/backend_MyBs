package com.smldb2.demo.controllers;

import com.smldb2.demo.Entity.Site;
import com.smldb2.demo.services.SiteService;
import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/sites")
@CrossOrigin(origins = "**")
public class SiteController {
    @Autowired
    private SiteService siteService;

    @GetMapping
    public ResponseEntity<List<Site>> getAllSites() {
        List<Site> sites = siteService.getAllSites();
        return ResponseEntity.ok(sites);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Site> getSiteById(@PathVariable String id) {
        return siteService.getSiteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/entite/{codeEntite}")
    public ResponseEntity<List<Site>> getSitesByCodeEntite(@PathVariable String codeEntite) {
        List<Site> sites = siteService.getSitesByCodeEntite(codeEntite);
        return ResponseEntity.ok(sites);
    }

    @GetMapping("/description/{description}")
    public ResponseEntity<Site> getSiteByDescription(@PathVariable String description) {
        return siteService.getSiteByDescription(description)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
