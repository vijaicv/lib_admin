package com.BookAdminService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BookAdminService.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer>{

	List<Book> findTopByOrderByIdDesc();

}
