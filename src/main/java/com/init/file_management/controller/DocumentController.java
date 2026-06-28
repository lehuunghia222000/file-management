package com.init.file_management.controller;

import com.init.file_management.dto.request.DocumentRequest;
import com.init.file_management.dto.request.UserCreationRequest;
import com.init.file_management.dto.request.UserUpdateRequest;
import com.init.file_management.dto.response.ApiResponse;
import com.init.file_management.dto.response.StatusCount;
import com.init.file_management.dto.response.UserResponse;
import com.init.file_management.entity.Document;
import com.init.file_management.entity.Users;
import com.init.file_management.enums.Status;
import com.init.file_management.service.DocumentService;
import com.init.file_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.io.Reader;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/file")
public class DocumentController {
    @Autowired
    private DocumentService documentService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ApiResponse<Page<Document>> getAllDocuments(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Status status,
            Pageable pageable,
            Principal principal) {
        Users currentUser = userService.findByUserName(principal.getName());
        return ApiResponse.<Page<Document>>builder()
                .result(documentService.documentList(title, code, category, status, pageable, currentUser))
                .build();
    }

    @PostMapping("/create")
    public ApiResponse<Document> createDocument(@RequestBody DocumentRequest dto, Principal principal) {
        Users user = userService.findByUserName(principal.getName());
        return ApiResponse.<Document>builder()
                .result(documentService.createDocument(dto, user))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Document> updateDocument(@PathVariable String id, @RequestBody DocumentRequest dto, Principal principal) {
        Users user = userService.findByUserName(principal.getName());
        return ApiResponse.<Document>builder()
                .result(documentService.updateDocument(id, dto, user))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteDocument(@PathVariable String id, Principal principal) {
        Users user = userService.findByUserName(principal.getName());
        return ApiResponse.<String>builder()
                .result(documentService.deleteDocument(id, user))
                .build();
    }

    @GetMapping("/status-count")
    public ResponseEntity<List<StatusCount>> getDocumentStatusCount() {
        return ResponseEntity.ok(documentService.getDocumentStatusCount());
    }
}
