package com.smldb2.demo.Entity;

public enum TypePrestataire {
    CONJOINT("Conjoint"),
    ENFANT("Enfant"),
    PERE("Père"),
    MERE("Mère");

    private final String dbValue;

    TypePrestataire(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    // Conversion DB → Enum
    public static TypePrestataire fromDbValue(String dbValue) {
        if (dbValue == null || dbValue.trim().isEmpty()) {
            return null; // accepte les valeurs nulles ou vides
        }
        for (TypePrestataire t : values()) {
            if (t.dbValue.equalsIgnoreCase(dbValue.trim())) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown value in DB: " + dbValue);
    }

}

