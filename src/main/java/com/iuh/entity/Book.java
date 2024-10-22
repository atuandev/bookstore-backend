package com.iuh.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "books")
public class Book extends AbstractEntity {

    String title;

    @Column(unique = true)
    String slug;

    @Column(columnDefinition = "TEXT")
    String description;

    String size;

    Integer pages;

    Integer weight;

    Integer publishYear;

    Double importPrice;

    Double price;

    @Builder.Default
    Integer stock = 0;

    @Builder.Default
    Integer sold = 0;

    @Builder.Default
    Boolean isNew = false;

    @Builder.Default
    Boolean isFeatured = false;

    @Builder.Default
    Boolean status = true;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToOne()
    @JoinColumn(name = "publisher_id")
    Publisher publisher;

    @ManyToOne()
    @JoinColumn(name = "discount_id")
    Discount discount;

    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    Set<BookImage> bookImages = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    Set<Author> authors = new HashSet<>();
}
