package com.iuh.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "authors")
public class Author extends AbstractEntity {

    String name;

    @ManyToOne()
    @JoinColumn(name = "book_id")
    Book book;
}
