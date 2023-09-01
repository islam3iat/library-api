package com.example.LibraryManagementSystem.book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Integer> {
    boolean existsBookById(int id);

}
