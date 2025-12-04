package com.smldb2.demo.DTO;

// MatStatDTO.java
public class MatStatDTO {
    private String mat;
    private long count;

    public MatStatDTO(String mat, long count) {
        this.mat = mat;
        this.count = count;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getMat() {
        return mat;
    }

    public void setMat(String mat) {
        this.mat = mat;
    }

    // getters et setters
}

