package com.example.LibraryManagementSystem.author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author,Integer> {
    Boolean existsAuthorById(int id);
    @Query("SELECT a FROM Author a WHERE a.firstName=?1 AND a.lastName=?2")
    Boolean existsAuthorByFirstNameAndAndLastName(String firstName,String lastName);
    Optional<Author> findAuthorByFirstNameAndLastName(String firstName,String lastName);
}
