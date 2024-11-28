package com.iuh.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "categories")
@ToString(callSuper = true)
public class Category extends AbstractEntity {
	@Column(unique = true)
    String name;

    @Column(unique = true)
    String slug;

    @Column(columnDefinition = "TEXT")
    String description;
    
    @ToString.Exclude
    @OneToMany(mappedBy="category", orphanRemoval = true)
    @JsonIgnore
    List<Book> books;

	public Category orElseThrow(Object object) {
		// TODO Auto-generated method stub
		return null;
	}
}
