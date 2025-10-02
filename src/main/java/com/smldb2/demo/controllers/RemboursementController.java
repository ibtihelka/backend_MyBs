package com.smldb2.demo.controllers;
import com.smldb2.demo.Entity.Remboursement;
import com.smldb2.demo.services.RemboursementService;
import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/remboursements")
@CrossOrigin(origins = "**")
public class RemboursementController {
    @Autowired
    private RemboursementService remboursementService;





    @GetMapping
    public ResponseEntity<List<Remboursement>> getAllRemboursements() {
        List<Remboursement> remboursements = remboursementService.getAllRemboursements();
        return ResponseEntity.ok(remboursements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Remboursement> getRemboursementById(@PathVariable String id) {
        return remboursementService.getRemboursementById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{persoId}")
    public ResponseEntity<List<Remboursement>> getRemboursementsByPersoId(@PathVariable String persoId) {
        List<Remboursement> remboursements = remboursementService.getRemboursementsByPersoId(persoId);
        return ResponseEntity.ok(remboursements);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Remboursement>> getRemboursementsByStatus(@PathVariable String status) {
        List<Remboursement> remboursements = remboursementService.getRemboursementsByStatus(status);
        return ResponseEntity.ok(remboursements);
    }

    @GetMapping("/bordereau/{refBordereau}")
    public ResponseEntity<List<Remboursement>> getRemboursementsByBordereau(@PathVariable String refBordereau) {
        List<Remboursement> remboursements = remboursementService.getRemboursementsByBordereau(refBordereau);
        return ResponseEntity.ok(remboursements);
    }
}
