package com.neeraj.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neeraj.model.Book;

public interface IBookRepository extends JpaRepository<Book, Integer> {

}
