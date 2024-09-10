package com.neeraj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neeraj.model.Book;
import com.neeraj.service.IBookService;


@Controller
@RequestMapping("/main")
public class MainController {

	@Autowired
	private IBookService service;
	
	@GetMapping("/book")
	public String showBooks(Model model, @PageableDefault(page = 0, size = 12)Pageable pageable) {
		Page<Book> books = service.readBookWithPagination(null, null, null, pageable);
		
		model.addAttribute("books", books.getContent());
		model.addAttribute("bookPage", books); 
		
		return "index";
	}
}
