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

    String author;

    String size;

    Integer pages;

    Integer weight;

    Integer publishYear;

    Double importPrice;

    Double price;

    Integer stock;

    Integer sold;

    Boolean isNew;

    Boolean isFeatured;

    Boolean status;

    @ManyToOne()
    @JoinColumn(name = "category_id", nullable = true)
    Category category;

    @ManyToOne()
    @JoinColumn(name = "publisher_id", nullable = true)
    Publisher publisher;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "discount_id", nullable = true)
    Discount discount;

    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    Set<BookImage> bookImages = new HashSet<>();

    public void addBookImage(BookImage bookImage) {
        if (bookImages == null) {
            bookImages = new HashSet<>();
        }
        bookImages.add(bookImage);
        bookImage.setBook(this);
    }
    
	public Double getDiscountPrice() {
		if (discount == null) {
			return price;
		}
		return price - (price * discount.getPercent() / 100);
	}
}
