package com.neeraj.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name="Book")
public class Book implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 60)
	private String title;
	
	@Column(length = 30)
	private String author;

	private Float price;

	private Integer pageNo;
	
	@Column(name = "Ebook")
	private Boolean isEbook;
	
	private Boolean available;
	
	private String imagePath;
	
	public Book() {}

	public Book(String title, String author, Float price, Integer pageNo, Boolean isEbook, Boolean available, String imagePath) {
		this.title = title;
		this.author = author;
		this.price = price;
		this.pageNo = pageNo;
		this.isEbook = isEbook;
		this.available = available;
		this.imagePath = imagePath;
	}
}
