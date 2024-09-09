package com.neeraj.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neeraj.Exception.BookException;
import com.neeraj.dao.IBookRepository;
import com.neeraj.model.Book;

@Service
public class BookServiceImpl implements IBookService {

	@Autowired
	private IBookRepository repository;

	@Override
	public void addBook(Book book) throws BookException {
		/*
		 * add book if book object is not null otherwise throw BookException
		 *
		 */
		
		if (book == null) {
			throw new BookException("Book Object must not be null!");
		}

		book = capitalizeOfBook(book);
		repository.save(book);
	}

	@Override
	public void removeBook(Integer id) throws BookException {
		/*
		 * remove book if book exists otherwise throw BookException
		 * 
		 */

		Book book = bookExits(id);
		if (book == null) {
			throw new BookException("Book not exists for given id!");
		}

		repository.delete(book);
	}

	@Override
	public void updateBook(Book book) throws BookException {
		/*
		 * update book if book is present at database otherwise throw BookException
		 * 
		 */

		if (book == null) {
			throw new BookException("Book Object must not be null!");
		}

		Book dbBook = bookExits(book.getId());
		if (dbBook == null) {
			throw new BookException("Book is not updated because book is not present at db!");
		}

		book = capitalizeOfBook(book);
		repository.save(book); // always updates book and not save the new book
	}

	@Override
	public Book bookExits(Integer id) throws BookException {
		/*
		 * accept id and find book equivalent to that id if book present than return
		 * same book object otherwise return null
		 * 
		 */

		if (id == null) {
			throw new BookException("Book id must not be null!");
		}

		Optional<Book> book = repository.findById(id);
		if (book.isEmpty()) {
			return null;
		}
		return book.get();
	}

	@Override
	public Page<Book> readBookWithPagination(Book book, Float minPrice, Float maxPrice, Pageable pageable)
			throws BookException {
		/*
		 * filter the book object with either title or author or price  
		 * otherwise just do pagination
		 * 
		 * throw BookException if pageable object is null
		 * 
		 */
		
		if (pageable == null) {
			throw new BookException("Pagable object must not be null!");
		}

		book = capitalizeOfBook(book);
		Page<Book> filteredBooks;
		if (book != null && book.getTitle() != null) {
			filteredBooks = repository.filterBookByTitle(book.getTitle(), pageable);
		} else if (book != null && book.getAuthor() != null) {
			filteredBooks = repository.filterBookByAuthor(book.getAuthor(), pageable);
		} else if (minPrice != null && maxPrice != null) {
			filteredBooks = repository.filterBookByPrice(minPrice, maxPrice, pageable);
		} else {
			filteredBooks = repository.findAll(pageable);
		}
		
		return filteredBooks;
	}

	private Book capitalizeOfBook(Book book) {
		
		if(book != null && book.getTitle() != null) {
			book.setTitle(book.getTitle().toUpperCase());
		}
		if(book != null && book.getAuthor() != null) {
			book.setAuthor(book.getAuthor().toUpperCase());
		}
		return book;
	}

}
