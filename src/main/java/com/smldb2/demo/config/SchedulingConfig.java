package com.smldb2.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configuration pour activer le scheduling dans l'application
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {
    // Le simple fait d'avoir cette annotation active le scheduling
}