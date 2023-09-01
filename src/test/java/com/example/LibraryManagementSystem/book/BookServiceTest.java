package com.example.LibraryManagementSystem.book;

import com.example.LibraryManagementSystem.AbstractTestContainer;
import com.example.LibraryManagementSystem.author.Author;
import com.example.LibraryManagementSystem.author.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest extends AbstractTestContainer {

    private BookService underTest;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        underTest=new BookService(bookRepository,authorRepository);
    }

    @Test
    void addBook() {
        // Given
        Author author=new Author("kmal","salm",Date.valueOf(LocalDate.of(1999,05,6)));
        when(authorRepository.save(any())).thenReturn(author);
        NewBook book=new NewBook("ll","kmal salm",LocalDate.of(1999,02,05),author);
        //When
        underTest.addBook(book);
        //Then
        ArgumentCaptor<Book> argumentCaptor=ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName()).isEqualTo(book.name());
        assertThat(argumentCaptor.getValue().getAuthorName()).isEqualTo(book.authorName());
        assertThat(argumentCaptor.getValue().getPublisherYear()).isEqualTo(book.publisherYear());
        assertThat(argumentCaptor.getValue().getAuthor()).isEqualTo(book.author());
    }

    @Test
    void getBooks() {
        //When
        underTest.getBooks();
        //Then
        verify(bookRepository).findAll();
    }

    @Test
    void getBook() {
        // Given
        int id=10;

        Book book=new Book("ll","kmal salm",LocalDate.of(1999,02,05));
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        //When
        Book actual = underTest.getBook(id);
        //Then
        assertThat(actual).isEqualTo(book);

    }

    @Test
    void removeBook() {
        // Given
        int id=10;
        when(bookRepository.existsBookById(id)).thenReturn(true);
        //When
        underTest.removeBook(id);
        //Then
        verify(bookRepository).deleteById(id);
    }

    @Test
    void updateBook() {
        // Given
        int id=10;
        Book book=new Book(id,"ll","kmal salm",LocalDate.of(1999,02,05));
        Author author=new Author("kmal","salm",Date.valueOf(LocalDate.of(1999,05,6)));
        NewBook update=new NewBook("hb","kmal salm",LocalDate.of(1999,02,05),author);
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        //When
        underTest.updateBook(id,update);
        //Then
        ArgumentCaptor<Book> bookArgumentCaptor=ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(bookArgumentCaptor.capture());
        assertThat(bookArgumentCaptor.getValue().getName()).isEqualTo(update.name());
        assertThat(bookArgumentCaptor.getValue().getAuthorName()).isEqualTo(update.authorName());
        assertThat(bookArgumentCaptor.getValue().getPublisherYear()).isEqualTo(update.publisherYear());
        assertThat(bookArgumentCaptor.getValue().getAuthor()).isEqualTo(update.author());

    }
}