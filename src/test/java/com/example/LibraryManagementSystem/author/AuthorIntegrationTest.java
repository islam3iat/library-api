package com.example.LibraryManagementSystem.author;

import com.example.LibraryManagementSystem.author.Author;
import com.example.LibraryManagementSystem.author.NewAuthor;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.DATE;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AuthorIntegrationTest {
    @Autowired
    private  WebTestClient webTestClient;
    private final static Random RANDOM=new Random();
    private final static String url="api/v1/authors";

    @Test
    void canRegisterAuthor() {
        Faker faker=new Faker();
        String firstName=faker.name().firstName();
        String lastName=faker.name().lastName();
        Date brithDate= Date.valueOf("2002-03-02");

        NewAuthor request=new NewAuthor(firstName,lastName,brithDate);
        webTestClient.post().
                uri(url).
                accept(MediaType.APPLICATION_JSON).
                contentType(MediaType.APPLICATION_JSON).body(Mono.just(request), NewAuthor.class).
                exchange().
                expectStatus().
                isOk();
        List<Author> allAuthors = webTestClient.get().uri(url).accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectBodyList(new ParameterizedTypeReference<Author>() {
        }).returnResult().getResponseBody();
        Author expectAuthor=new Author(firstName,lastName,brithDate);
        assertThat(allAuthors).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id").contains(expectAuthor);
        int id=allAuthors.stream().filter(author -> author.getDateOfBrith().equals(brithDate)).map(Author::getId).findFirst().orElseThrow();
        expectAuthor.setId(id);
        webTestClient.get().uri(url+"/{id}",id).accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectBodyList(new ParameterizedTypeReference<Author>() {
        }).contains(expectAuthor);
    }
    @Test
    void canDeleteAuthor() {
        Faker faker=new Faker();
        String firstName=faker.name().firstName();
        String lastName=faker.name().lastName();
        Date brithDate= Date.valueOf("1980-02-02");
        NewAuthor request=new NewAuthor(firstName,lastName,brithDate);
        webTestClient.post().
                uri(url).
                accept(MediaType.APPLICATION_JSON).
                contentType(MediaType.APPLICATION_JSON).body(Mono.just(request), NewAuthor.class).
                exchange().
                expectStatus().
                isOk();
        List<Author> allAuthors = webTestClient.get().uri(url).accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectBodyList(new ParameterizedTypeReference<Author>() {
        }).returnResult().getResponseBody();
        Author expectAuthor=new Author(firstName,lastName,brithDate);
        assertThat(allAuthors).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id").contains(expectAuthor);
        int id=allAuthors.stream().filter(author -> author.getDateOfBrith().equals(brithDate)).map(Author::getId).findFirst().orElseThrow();
        expectAuthor.setId(id);
        webTestClient.delete()
                .uri(url+"/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus()
                .isOk();
        webTestClient.get().uri(url+"{id}",id).accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isNotFound();

    }

    public NewAuthor createFakerAuthor(){
        Faker faker=new Faker();
        String firstName=faker.name().firstName();
        String lastName=faker.name().lastName();

        Date brithDate= Date.valueOf("1980-02-10");
        NewAuthor request=new NewAuthor(firstName,lastName,brithDate);
        return request;
    }
    @Test
    void canUpdateAuthor() {
        var request=createFakerAuthor();

        webTestClient.post().
                uri(url).
                accept(MediaType.APPLICATION_JSON).
                contentType(MediaType.APPLICATION_JSON).body(Mono.just(request), NewAuthor.class).
                exchange().
                expectStatus().
                isOk();
        List<Author> allAuthors = webTestClient.get().uri(url).accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectBodyList(new ParameterizedTypeReference<Author>() {
        }).returnResult().getResponseBody();
        int id=allAuthors.stream().filter(author -> author.getLastName().equals(request.lastName())).map(Author::getId).findFirst().orElseThrow();
        Faker faker=new Faker();
        String firstName=faker.name().firstName();
        String lastName=faker.name().lastName();

        Date brithDate= Date.valueOf("1980-02-05");
        NewAuthor updateRequest=new NewAuthor(firstName,lastName,brithDate);




        webTestClient.put().
                uri(url+"/{id}",id).
                accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).
                body(Mono.just(updateRequest), NewAuthor.class).
                exchange().
                expectStatus().
                isOk();
        Author responseBody = webTestClient.get().
                uri(url + "/{id}", id).
                accept(MediaType.APPLICATION_JSON).
                exchange().
                expectStatus().
                isOk().
                expectBody(new ParameterizedTypeReference<Author>() {
                }).
                returnResult().
                getResponseBody();
        assertThat(responseBody).isEqualToIgnoringGivenFields(updateRequest,"id","books");
    }




}
