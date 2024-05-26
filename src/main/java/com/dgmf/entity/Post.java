package com.dgmf.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
        name = "posts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"title"})
        }
)
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "content", nullable = false)
    private String content;
    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Comment> comments = new HashSet<>();
    // OneToMany Unidirectional Relationship Between Post and
    // Category Entities (Parent ==> Category / Post ==> Child)
    @ManyToOne(fetch = FetchType.LAZY) // Fetch Category on Demand (Not
    // immediately) by Calling the Related Getter
    // To Specify the Foreign Key Name
    @JoinColumn(name = "category_id")
    private Category category;
}
