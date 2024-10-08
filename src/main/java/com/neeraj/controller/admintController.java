package com.neeraj.controller;

import java.io.BufferedOutputStream;
import java.io.File;
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

	private String restBookImagePath = "src/main/resources/static/img/books/";

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
		model.addAttribute("Book", service.bookExits(Integer.parseInt(id)));
		return "Admin/UpdateBook";
	}
	
	@GetMapping("/deleteBookUI")
	public String adminDeleteBookUI(@RequestParam("id") String id, Model model) {
		// hard delete the entry from database and delete the related image file from server 
		
		// deleletion of image file from server
		Book deletingBook = service.bookExits(Integer.parseInt(id));
		File deletingFile = new File(restBookImagePath + deletingBook.getImagePath());
		deletingFile.delete();
		
		// deletion of entry from database
		service.removeBook(Integer.parseInt(id));
		
		model.addAttribute("successMsg", "Details are sucessfully deleted...");
		model.addAttribute("backPagePath", "book");
		return "Admin/Success";
	}

	/*
	 * below method is for pagination service for admin url : /admin/book method
	 * type : get return : "Admin/read.html"
	 */
	@GetMapping("/book")
	public String showBooks(Model model, @PageableDefault(page = 0, size = 8) Pageable pageable) {
		Page<Book> books = service.readBookWithPagination(null, null, null, pageable);
		model.addAttribute("books", books.getContent());
		model.addAttribute("bookPage", books);

		return "Admin/read";
	}

	/*
	 * below method is for saving the book object url : /admin/saveBook method type
	 * : post return : "Admin/Success.html"
	 */
	@PostMapping("/saveBook")
	public String adminSaveBook(@ModelAttribute("Book") Book book, @RequestParam("image") MultipartFile image,
			Model model) {
		String bookFileName = service.getBookFileName(book.getTitle());
		System.out.println(bookFileName);
		
		String bookPath = restBookImagePath + bookFileName;
		book.setImagePath(bookFileName);

		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(bookPath)))) {
			// saves image at server side
			bos.write(image.getBytes());
			bos.flush();
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
	 * description : below method is for update the book object 
	 * url : /admin/updateBook 
	 * method type : post 
	 * return : "Admin/Success.html"
	 */
	@PostMapping("/updateBook")
	public String adminUpdateBook(@ModelAttribute("Book") Book book, @RequestParam("image") MultipartFile image,
			Model model) {
		Book oldBook = service.bookExits(book.getId());
		String oldBookLoc = restBookImagePath + oldBook.getImagePath();

		if (!image.isEmpty() && oldBook.getTitle().equalsIgnoreCase(book.getTitle())) {
			// image is provided and title is same so file name will also be same
			// only change the content of file on the server side

			try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(oldBookLoc)))) {
				bos.write(image.getBytes());
				bos.flush();
			} catch (IOException e) {
				System.out.println("Image is File is not saved...");
				e.printStackTrace();
			}
			book.setImagePath(oldBook.getImagePath());
			service.updateBook(book);
		} else if (!image.isEmpty()) {
			// image is provided and title is not same so file name has to be changed
			// 1. delete old image file from server
			// 2. create new file with new data and new fileName
			// 3. save new fileName at database 
			
			// deleting the old image file from server side
			File oldFile = new File(oldBookLoc);
			oldFile.delete();
			
			// creating the new image file provided 
			String newFileName = service.getBookFileName(book.getTitle());
			try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(restBookImagePath + newFileName)))) {
				bos.write(image.getBytes());
				bos.flush();
			} catch (IOException e) {
				System.out.println("Image is File is not saved...");
				e.printStackTrace();
			}
			
			// new image path is saved at database
			book.setImagePath(newFileName);
			service.updateBook(book);
		} else if (oldBook.getTitle().equalsIgnoreCase(book.getTitle())) {
			// title is not changed and image is not provided
			// old image path will be set as it is
				
			book.setImagePath(oldBook.getImagePath());
			service.updateBook(book);
		} else {
			// title is get changed and image is also not provided
			// 1. image file at server also get renamed according to title name
			// 2. image path will get changed 
			
			// renaming the existing file with new name according to title name
			String newFileName = service.getBookFileName(book.getTitle());
			String newFilePath = restBookImagePath + newFileName;
			File olderFile = new File(restBookImagePath + oldBook.getImagePath());
			olderFile.renameTo(new File(newFilePath));

			// saving new image path at database
			book.setImagePath(newFileName);
			service.updateBook(book);
		}

		model.addAttribute("successMsg", "Details are sucessfully updated...");
		model.addAttribute("backPagePath", "book");
		return "Admin/Success";
	}

}
