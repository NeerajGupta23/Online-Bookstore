package com.neeraj.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.neeraj.model.Book;

public interface IBookRepository extends JpaRepository<Book, Integer> {

	@Query("FROM Book WHERE title like %:title%")
	Page<Book> filterBookByTitle(String title, Pageable pageable);

	@Query("FROM Book WHERE author like %:author%")
	Page<Book> filterBookByAuthor(String author, Pageable pageable);
	
	@Query("FROM Book WHERE price >= :minPrice and price <= :maxPrice")
	Page<Book> filterBookByPrice(Float minPrice, Float maxPrice, Pageable pageable);
	
	@Query("select count(b.id) + 1 from Book b where b.imagePath like CONCAT(:title, '_%.webp')")
	Integer getNextBookNumber(String title);
} 
