package com.sam.newsapp.entity;
import java.util.ArrayList;
import java.util.List;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "users")
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NonNull
    private String username;
    @NonNull
    private String password;
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
private List<journalEntity> journalEntities = new ArrayList<>();
private List<String> roles;

}
