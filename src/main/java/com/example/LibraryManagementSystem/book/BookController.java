package com.example.LibraryManagementSystem.book;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;
    @GetMapping
    public List<Book> getBooks(){
        return bookService.getBooks();
    }
    @GetMapping("{book_id}")
    public  Book getbook(@PathVariable("book_id") int id){
        return bookService.getBook(id);
    }
    @PostMapping
    public void addBook(@Valid @RequestBody NewBook request){
        bookService.addBook(request);
    }
    @DeleteMapping("{book_id}")
    public void removeBook(@PathVariable("book_id") int id){
        bookService.removeBook(id);
    }
    @PutMapping("{book_id}")
    public void updateBook(@PathVariable("book_id") int id,@RequestBody NewBook update){
        bookService.updateBook(id, update);
    }
}
