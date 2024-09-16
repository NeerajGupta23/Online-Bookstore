package com.neeraj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neeraj.model.Book;
import com.neeraj.service.IBookService;


@Controller
@RequestMapping("/admin")
public class admintController {

	@Autowired
	private IBookService service;
	
	@GetMapping("/home")
	public String adminHome(Model model) {
		return "Admin/HomePage"; 
	}
	
	@GetMapping("/insertBookUI")
	public String adminInsertBookUI(Model model) {
		return "Admin/InsertBook"; 
	}
	
	/*
	* 	below method is for pagination service for admin
	*	url : /admin/book
	*	method type : get
	*	return : "Admin/read.html"
	*/
	
	@GetMapping("/book")
	public String showBooks(Model model, @PageableDefault(page = 0, size = 8)Pageable pageable) {
		Page<Book> books = service.readBookWithPagination(null, null, null, pageable);
		System.out.println(books.getNumber());
		System.out.println(books.getTotalPages());
		
		model.addAttribute("books", books.getContent());
		model.addAttribute("bookPage", books); 
		 
		return "Admin/read"; 
	}
	
	
	/*
	* 	below method is for saving the book object
	*	url : /admin/saveBook
	*	method type : post
	*	return : "Admin/Success.html"
	*/
	
	@PostMapping("/saveBook")
	public String adminSaveBook(Model model, @ModelAttribute Book book) {
		// incomplete
		return "Admin/Success"; 
	}
	
}
