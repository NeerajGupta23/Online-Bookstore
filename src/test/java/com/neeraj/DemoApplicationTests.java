package com.neeraj;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import com.neeraj.model.Book;
import com.neeraj.service.IBookService;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	IBookService bookService;
	
	@Test
	void contextLoads() {
//		bookService.addBook(new Book("Maths", "Neeraj Kumar", 2500.0F, 1500, true, true));
//		bookService.addBook(new Book("DSA", "Ujjwal", 1500.0F, 1600, true, true));
//		bookService.addBook(new Book("DBMS", "Miheer", 2000.0F, 500, true, true));
//		bookService.addBook(new Book("OS", "Ronak", 2500.0F, 900, true, true));
//		bookService.addBook(new Book("COA", "Priyanshu", 300.0F, 1100, true, true));
		
//		Book book = new Book();
//		book.setAuthor("neeraj");
//		System.out.println(bookService.readBookWithPagination(book, null, null, PageRequest.of(0, 3)).getContent());

//		System.out.println(bookService.readBookWithPagination(null, 300.0F, 2500.0f, PageRequest.of(0, 3)).getContent());
//		System.out.println(bookService.readBookWithPagination(null, 300.0F, 2500.0f, PageRequest.of(1, 3)).getContent());

		System.out.println(bookService.readBookWithPagination(null, null, null, PageRequest.of(0, 3)).getContent());
		System.out.println(bookService.readBookWithPagination(null, null, null, PageRequest.of(1, 3)).getContent());
		System.out.println(bookService.readBookWithPagination(null, null, null, PageRequest.of(2, 3)).getContent());
		System.out.println(bookService.readBookWithPagination(null, null, null, PageRequest.of(3, 3)).getContent());
		
		System.out.println(bookService);
		assertNotNull(bookService);
	}

}
