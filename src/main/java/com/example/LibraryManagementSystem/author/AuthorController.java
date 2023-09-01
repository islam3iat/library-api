package com.example.LibraryManagementSystem.author;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/authors")
public class AuthorController {
    private final AuthorService authorService;
@GetMapping
public List<Author> getAuthors(){
    return authorService.getAuthors();
}
@GetMapping("{authorId}")
public Author getAuthor(@PathVariable("authorId") int id){
    return authorService.getAuthor(id);
}
@PostMapping
public void addAuthor(@Valid @RequestBody NewAuthor request){
    authorService.addAuthor(request);
}
@DeleteMapping("{authorId}")
public void deleteAuthor(@PathVariable("authorId") int id){
    authorService.removeAuthor(id);
}
@PutMapping("{authorId}")
public void updateAuthor(@PathVariable("authorId") int id,@RequestBody NewAuthor update){
    authorService.updateAuthor(id, update);
}



}
