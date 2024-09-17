package com.neeraj.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.neeraj.model.Book;
import com.neeraj.service.IBookService;


@Controller
@RequestMapping("/admin")
public class admintController {

	@Autowired
	private IBookService service;
	
	@GetMapping("/home")
	public String adminHome() {
		return "Admin/HomePage"; 
	}
	
	@GetMapping("/insertBookUI")
	public String adminInsertBookUI(Model model) {
		model.addAttribute("Book", new Book());
		return "Admin/InsertBook"; 
	}
	
	@GetMapping("/updateBookUI")
	public String adminUpdateBookUI(@RequestParam("id") String id, Model model) {
		// book equivalent to given id is always present
		System.out.println(id);
		
		model.addAttribute("Book", service.bookExits(Integer.parseInt(id)));
		return "Admin/UpdateBook"; 
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
	public String adminSaveBook(@ModelAttribute("Book") Book book,@RequestParam("image") MultipartFile image, Model model) {
		String bookFileName = service.getBookFileName(book.getTitle())+".webp";
		String bookPath = "src/main/resources/static/img/books/" + bookFileName;
		book.setImagePath(bookFileName);
		
		try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(bookPath)))) {
			// saves image at server side 
			bos.write(image.getBytes());
		} catch (IOException e) {
			System.out.println("Image is File is not saved...");
			e.printStackTrace();
		}
		
		// store entry at database side
		service.addBook(book);
		
		model.addAttribute("successMsg", "Details are sucessfully saved...");
		model.addAttribute("backPagePath", "insertBookUI");
		return "Admin/Success"; 
	}
	
	/*
	 * 	below method is for update the book object
	 *	url : /admin/updateBook
	 *	method type : post
	 *	return : "Admin/Success.html"
	 */
	@PostMapping("/updateBook")
	public String adminUpdateBook(@ModelAttribute("Book") Book book,@RequestParam("image") MultipartFile image, Model model) {
		System.out.println(image.isEmpty());
		if(image.isEmpty()) {
		 	
		} else {
			
		}
		
		model.addAttribute("successMsg", "Details are sucessfully updated...");
		model.addAttribute("backPagePath", "book");
		return "Admin/Success"; 
	}
	
}
