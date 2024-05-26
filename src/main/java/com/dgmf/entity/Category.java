package com.dgmf.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "categories")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    // OneToMany Bidirectional Relationship Between Post and
    // Category Entities (Parent ==> Category / Post ==> Child)
    @OneToMany(
            mappedBy = "category", // Do Not Need to Create
            // Additional Join Table
            cascade = CascadeType.ALL, // Performed Operations on
            // Parent will also Be Done on Child as well
            orphanRemoval = true // Remove Orphaned Children
    )
    private List<Post> posts;
}
