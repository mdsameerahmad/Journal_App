package com.sam.newsapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "journal_entries")
@Getter
@Setter
@NoArgsConstructor           
@AllArgsConstructor          
@Builder  

public class journalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the ID

    private Long id;
    private String title;
    private String description;

    //to connect jounal entity with the user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore 
    private Users user;

    
}
