package com.init.file_management.dto.request;

import lombok.Data;

@Data
public class DocumentRequest {
    private String code;
    private String title;
    private String description;
    private String category;
    private String fileName;
}
