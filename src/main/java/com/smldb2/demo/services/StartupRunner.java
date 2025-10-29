package com.smldb2.demo.services;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final CsvService csvService;

    public StartupRunner(CsvService csvService) {
        this.csvService = csvService;
    }

    @Override
    public void run(String... args) {
        csvService.importCsv();
    }
}

