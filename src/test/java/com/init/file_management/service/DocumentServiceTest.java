package com.init.file_management.service;

import com.init.file_management.dto.request.DocumentRequest;
import com.init.file_management.entity.Document;
import com.init.file_management.entity.Users;
import com.init.file_management.enums.Roles;
import com.init.file_management.exception.ApplicationException;
import com.init.file_management.repository.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {
    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentService documentService;

    @Test
    void updateDocument_Success() {
        // Given
        String id = "doc-123";
        Users staff = new Users();
        staff.setId("user-1");
        staff.setRole(Roles.STAFF);

        Document doc = new Document();
        doc.setId(id);
        doc.setCreatedBy(staff);

        DocumentRequest request = new DocumentRequest();
        request.setTitle("New Title");

        when(documentRepository.findById(id)).thenReturn(Optional.of(doc));
        when(documentRepository.save(any(Document.class))).thenReturn(doc);

        // When
        Document result = documentService.updateDocument(id, request, staff);

        // Then
        assertEquals("New Title", result.getTitle());
        verify(documentRepository, times(1)).save(doc);
    }

    @Test
    void updateDocument_AccessDenied_ThrowsException() {
        // Given
        String id = "doc-123";
        Users staff1 = new Users(); // User hiện tại
        staff1.setId("user-1");
        staff1.setRole(Roles.STAFF);

        Users staff2 = new Users(); // Chủ sở hữu document
        staff2.setId("user-2");

        Document doc = new Document();
        doc.setId(id);
        doc.setCreatedBy(staff2);

        when(documentRepository.findById(id)).thenReturn(Optional.of(doc));

        // When & Then
        assertThrows(ApplicationException.class, () -> {
            documentService.updateDocument(id, new DocumentRequest(), staff1);
        });
    }

    @Test
    void deleteDocument_Admin_Success() {
        // Given
        String id = "doc-123";
        Users admin = new Users();
        admin.setRole(Roles.ADMIN);

        when(documentRepository.findById(id)).thenReturn(Optional.of(new Document()));

        // When
        String result = documentService.deleteDocument(id, admin);

        // Then
        assertEquals("Deletion completed!", result);
        verify(documentRepository, times(1)).delete(any(Document.class));
    }
}
