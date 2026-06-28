package com.init.file_management.service;
import com.init.file_management.dto.request.DocumentRequest;
import com.init.file_management.dto.response.StatusCount;
import com.init.file_management.entity.Document;
import com.init.file_management.entity.Users;
import com.init.file_management.enums.Roles;
import com.init.file_management.enums.Status;
import com.init.file_management.exception.ApplicationException;
import com.init.file_management.exception.ErrorCode;
import com.init.file_management.repository.DocumentRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    public Page<Document> documentList(String title, String code, String category,
                                       Status status, Pageable pageable, Users currentUser) {
        Specification<Document> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (title != null) predicates.add(cb.like(root.get("title"), "%" + title + "%"));
            if (code != null) predicates.add(cb.equal(root.get("code"), code));
            if (category != null) predicates.add(cb.equal(root.get("category"), category));
            if (status != null) predicates.add(cb.equal(root.get("status"), status));
            if (currentUser.getRole().equals(Roles.STAFF)) {
                predicates.add(cb.equal(root.get("createdBy").get("id"), currentUser.getId()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return documentRepository.findAll(spec, pageable);
    }

    public Document createDocument(DocumentRequest dto, Users user) {
        Document doc = new Document();
        doc.setTitle(dto.getTitle());
        doc.setCode(dto.getCode());
        doc.setDescription(dto.getDescription());
        doc.setCategory(dto.getCategory());
        doc.setFileName(dto.getFileName());
        doc.setCreatedBy(user);
        doc.setStatus(Status.DRAFT.name());
        doc.setCreatedAt(LocalDate.now());
        return documentRepository.save(doc);
    }

    public Document updateDocument(String id, DocumentRequest dto, Users user) {
        Document doc = documentRepository.findById(id).orElseThrow();

        if (user.getRole().equals(Roles.STAFF) && !doc.getCreatedBy().getId().equals(user.getId())) {
            throw new ApplicationException(ErrorCode.ACCESS_DENIED_UPDATE);
        }
        doc.setTitle(dto.getTitle());
        doc.setCode(dto.getCode());
        doc.setCategory(dto.getCategory());
        doc.setDescription(dto.getDescription());
        doc.setFileName(dto.getFileName());
        return documentRepository.save(doc);
    }

    public String deleteDocument(String id, Users user) {
        Document doc = documentRepository.findById(id).orElseThrow();
        if (!user.getRole().equals(Roles.ADMIN)) {
            throw new ApplicationException(ErrorCode.ACCESS_DENIED_DELETE);
        }
        documentRepository.delete(doc);
        return "Deletion completed!";
    }

    public List<StatusCount> getDocumentStatusCount() {
        return documentRepository.countDocumentsByStatus();
    }
}
