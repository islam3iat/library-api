package com.example.LibraryManagementSystem.book;

import com.example.LibraryManagementSystem.author.Author;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class BookIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;
    private final static String url="api/v1/books";
    private final static Random RANDOM=new Random();

    public NewBook createFakeBook() {
        Faker faker = new Faker();

        String fullName = faker.name().fullName();
        NewBook book = new
                NewBook(faker.name().title(),
                fullName,
                LocalDate.of(2002, 02, RANDOM.nextInt(1, 12))
                , new Author(fullName.split(" ")[0], fullName.split(" ")[1], null));
        return book;
    }
    @Test
    public void CanRegisterBook(){
        NewBook request=createFakeBook();
        webTestClient.post().
                uri(url).
                accept(MediaType.APPLICATION_JSON).
                contentType(MediaType.APPLICATION_JSON).
                body(Mono.just(request),NewBook.class).
                exchange().
                expectStatus().
                isOk();
        List<Book> allBooks = webTestClient.get().uri(url).accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectBodyList(new ParameterizedTypeReference<Book>() {
        }).returnResult().getResponseBody();
        Book expectResponse=new Book(request.name(),request.authorName(),request.publisherYear());
        Integer id = allBooks.stream().filter(book -> book.getName().equals(expectResponse.getName())).map(Book::getId).findFirst().orElseThrow();
        expectResponse.setId(id);
        webTestClient.get().uri(url+"/{id}",id).accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectBodyList(new ParameterizedTypeReference<Book>() {
        }).contains(expectResponse);

    }
    @Test
    public void CanDeleteBook(){
        NewBook request=createFakeBook();
        webTestClient.post().
                uri(url).
                accept(MediaType.APPLICATION_JSON).
                contentType(MediaType.APPLICATION_JSON).
                body(Mono.just(request),NewBook.class).
                exchange().
                expectStatus().
                isOk();
        List<Book> allBooks = webTestClient.get().uri(url).accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectBodyList(new ParameterizedTypeReference<Book>() {
        }).returnResult().getResponseBody();
        Book expectResponse=new Book(request.name(),request.authorName(),request.publisherYear());
        Integer id = allBooks.stream().filter(book -> book.getName().equals(expectResponse.getName())).map(Book::getId).findFirst().orElseThrow();
        expectResponse.setId(id);
        webTestClient.delete().uri(url+"/{id}",id).accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk();

        webTestClient.get().uri(url+"/{id}",id).accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isNotFound();
    }
    @Test
    public void CanUpdateBook(){
        NewBook request=createFakeBook();
        webTestClient.post().
                uri(url).
                accept(MediaType.APPLICATION_JSON).
                contentType(MediaType.APPLICATION_JSON).
                body(Mono.just(request),NewBook.class).
                exchange().
                expectStatus().
                isOk();
        List<Book> allBooks = webTestClient.get().uri(url).accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectBodyList(new ParameterizedTypeReference<Book>() {
        }).returnResult().getResponseBody();
        Integer id = allBooks.stream().filter(book -> book.getName().equals(request.name())).map(Book::getId).findFirst().orElseThrow();
        NewBook update=createFakeBook();
        webTestClient.put().
                uri(url+"/{id}",id).
                accept(MediaType.APPLICATION_JSON).
                contentType(MediaType.APPLICATION_JSON).
                body(Mono.just(update), NewBook.class).
                exchange().
                expectStatus().
                isOk();

        Book responseBody = webTestClient.get().
                uri(url + "/{id}", id).
                accept(MediaType.APPLICATION_JSON).
                exchange().
                expectStatus().
                isOk().
                expectBody(new ParameterizedTypeReference<Book>() {
                }).
                returnResult().
                getResponseBody();
        responseBody.setId(0);
        assertThat(responseBody)
                .usingRecursiveComparison()
                .ignoringFields("id", "borrows", "author.id")
                .isEqualTo(update);
    }



}
