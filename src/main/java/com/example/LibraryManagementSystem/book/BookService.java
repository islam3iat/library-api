package com.example.LibraryManagementSystem.book;

import com.example.LibraryManagementSystem.Exception.ApiRequestException;
import com.example.LibraryManagementSystem.Exception.NotFoundException;
import com.example.LibraryManagementSystem.author.Author;
import com.example.LibraryManagementSystem.author.AuthorRepository;
import com.example.LibraryManagementSystem.author.NewAuthor;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    @Transactional
    public void addBook(NewBook request){
        String[] fullName = request.authorName().split(" ");
        String firstName=fullName[0];
        String lastName=fullName[1];
        Author author = authorRepository.
                findAuthorByFirstNameAndLastName(firstName, lastName).
                orElseGet(() -> authorRepository.
                        save(new Author(firstName, lastName, null)));
        Book book=new Book();
        book.setAuthor(author);
        book.setName(request.name());
        book.setAuthorName(request.authorName());
        book.setPublisherYear(request.publisherYear());
        bookRepository.save(book);
    }
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }
    public Book getBook(int id){
        return bookRepository.findById(id).
                orElseThrow(() -> {
                    NotFoundException notFoundException = new NotFoundException("book with [%s] not found".formatted(id));
                    log.error("error is getting book with id {}",id,notFoundException);
                    return notFoundException;
                });
    }
    public void removeBook(int id){
        if(!bookRepository.existsBookById(id))
            throw new NotFoundException("book with [%s] not found".formatted(id));
        bookRepository.deleteById(id);
    }
    public void updateBook(int id,NewBook update){
        Book book=bookRepository.
                findById(id).
                orElseThrow(() -> new NotFoundException("book with [%s] not found".formatted(id)));
        boolean changes=false;
        if(update.name()!=null&&!update.name().equals(book.getName())){
            book.setName(update.name());
            changes=true;
        }
        if(update.authorName()!=null&&!update.authorName().equals(book.getAuthorName())){
            book.setAuthorName(update.authorName());
            changes=true;
        }
        if(update.publisherYear()!=null&&!update.publisherYear().equals(book.getPublisherYear())){
            book.setPublisherYear(update.publisherYear());
            changes=true;
        }
        if(update.author()!=null&&!update.author().equals(book.getAuthor())){
            book.setAuthor(update.author());
            changes=true;
        }
        if(!changes)
            throw new ApiRequestException("no change has be made");
        bookRepository.save(book);
    }
}
