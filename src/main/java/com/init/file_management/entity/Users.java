package com.init.file_management.entity;

import com.init.file_management.enums.Roles;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String userName;
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Roles role;
}
