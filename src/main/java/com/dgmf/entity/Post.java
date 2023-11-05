package com.dgmf.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(
        name = "posts",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "title_unique", // Entity Attribute name
                        columnNames = "title" // DB Column name
                )
        }
)
public class Post {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "posts_generator"
    )
    @SequenceGenerator(
            name = "posts_generator",
            sequenceName = "posts_sequence_name",
            allocationSize = 1
    )
    private Long id;
    @Column(name = "title", nullable = false, length = 50)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false, unique = true)
    private String content;
    @Column(name = "creation_date", nullable = false)
    // Hibernate will automatically take the current Timestamp of the JVM
    @CreationTimestamp
    private LocalDateTime creationDate;
    @Column(name = "last_update")
    // Hibernate will automatically take the current Timestamp of the JVM
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}
