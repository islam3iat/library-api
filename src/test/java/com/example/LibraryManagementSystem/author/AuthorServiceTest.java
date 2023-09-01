package com.example.LibraryManagementSystem.author;

import com.example.LibraryManagementSystem.author.Author;
import com.example.LibraryManagementSystem.author.AuthorRepository;
import com.example.LibraryManagementSystem.author.AuthorService;
import com.example.LibraryManagementSystem.author.NewAuthor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {
private AuthorService underTest;
@Mock
private AuthorRepository authorRepository;
    @BeforeEach
    void setUp() {
        underTest=new AuthorService(authorRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addAuthor() {
        // Given
        NewAuthor author=new NewAuthor("sam","mr", Date.valueOf("2002-03-05"));
        //When
        underTest.addAuthor(author);
        //Then
        ArgumentCaptor<Author> authorArgumentCaptor=ArgumentCaptor.forClass(Author.class);
        verify(authorRepository).save(authorArgumentCaptor.capture());
        assertThat(authorArgumentCaptor.getValue().getFirstName()).isEqualTo(author.firstName());
        assertThat(authorArgumentCaptor.getValue().getLastName()).isEqualTo(author.lastName());
        assertThat(authorArgumentCaptor.getValue().getDateOfBrith()).isEqualTo(author.dateOfBrith());
    }

    @Test
    void getAuthors() {
        //When
        underTest.getAuthors();
        //Then
        verify(authorRepository).findAll();

    }

    @Test
    void getAuthor() {

        // Given
        int id=12;
        Author author=new Author(id,"sam","mr", Date.valueOf("2002-03-05"));
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        //When
        Author actual=underTest.getAuthor(id);
        //Then
        assertThat(actual).isEqualTo(author);

    }

    @Test
    void removeAuthor() {
        // Given
        int id=12;
        when(authorRepository.existsAuthorById(id)).thenReturn(true);
        //When
        underTest.removeAuthor(id);
        //Then
        verify(authorRepository).deleteById(id);
    }

    @Test
    void updateAuthor() {
        // Given
        int id=10;
        Author author=new Author(id,"sam","mr", Date.valueOf("2002-03-05"));
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        NewAuthor  updateRequest=new NewAuthor("ss","dd",Date.valueOf("2003-02-04"));
        //When
        underTest.updateAuthor(id,updateRequest);

        //Then
        ArgumentCaptor<Author> authorArgumentCaptor=ArgumentCaptor.forClass(Author.class);
        verify(authorRepository).save(authorArgumentCaptor.capture());
        assertThat(authorArgumentCaptor.getValue().getFirstName()).isEqualTo(updateRequest.firstName());
        assertThat(authorArgumentCaptor.getValue().getLastName()).isEqualTo(updateRequest.lastName());
        assertThat(authorArgumentCaptor.getValue().getDateOfBrith()).isEqualTo(updateRequest.dateOfBrith());

    }
}