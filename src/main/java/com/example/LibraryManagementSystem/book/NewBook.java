package com.example.LibraryManagementSystem.book;

import com.example.LibraryManagementSystem.author.Author;

import java.time.LocalDate;

public record NewBook(String name, String authorName, LocalDate publisherYear, Author author) {
}
