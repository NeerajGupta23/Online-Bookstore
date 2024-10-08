package com.neeraj.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.neeraj.Exception.BookException;
import com.neeraj.model.Book;

public interface IBookService {
	void addBook(Book book) throws BookException;
	void removeBook(Integer id) throws BookException;
	void updateBook(Book book) throws BookException;
	Book bookExits(Integer id) throws BookException;
	Page<Book> readBookWithPagination(Book book, Float minPrice, Float maxPrice, Pageable pageable) throws BookException;	
	String getBookFileName(String title) throws BookException;
}
