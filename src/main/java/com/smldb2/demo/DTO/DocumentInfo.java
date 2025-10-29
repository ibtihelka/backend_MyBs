package com.smldb2.demo.DTO;



import org.springframework.web.multipart.MultipartFile;

public class DocumentInfo {
    private String documentType;
    private MultipartFile file;

    public DocumentInfo(String documentType, MultipartFile file) {
        this.documentType = documentType;
        this.file = file;
    }

    public String getDocumentType() {
        return documentType;
    }

    public MultipartFile getFile() {
        return file;
    }
}

