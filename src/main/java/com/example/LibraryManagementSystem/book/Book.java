package com.example.LibraryManagementSystem.book;

import com.example.LibraryManagementSystem.author.Author;
import com.example.LibraryManagementSystem.borrow.Borrow;
import jakarta.persistence.*;
import com.example.LibraryManagementSystem.member.Member;
import lombok.Getter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Book")
@Table(name = "book",uniqueConstraints = @UniqueConstraint(name = "book_name_unique",columnNames = "name"))
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name",columnDefinition ="VARCHAR(50)" )
    private String name;
    @Column(name = "author_name",columnDefinition = "VARCHAR(255)")
    private String authorName;
    @Column(name = "publisher_year")
    private LocalDate publisherYear;
    @Getter
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "author_id",
    nullable = false,referencedColumnName = "id",
    foreignKey = @ForeignKey(name = "book_author_id_fk"))
    private Author author;
//    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
//    @JoinTable(name = "borrow",
//   joinColumns =@JoinColumn( name = "book_id",
//           foreignKey = @ForeignKey(name ="borrow_book_id_fk" )),
//            inverseJoinColumns = @JoinColumn(name ="author_id",
//           foreignKey = @ForeignKey(name = "borrow_author_id_fk"))
//    )
    @OneToMany(
            cascade = {CascadeType.PERSIST,CascadeType.REMOVE},
            mappedBy = "book"
    )

    private List<Borrow> borrows=new ArrayList<>();

    public Book(int id, String name, String authorName, LocalDate publisherYear) {
        this.id = id;
        this.name = name;
        this.authorName = authorName;
        this.publisherYear = publisherYear;
    }

    public Book(String name, String authorName, LocalDate publisherYear) {
        this.name = name;
        this.authorName = authorName;
        this.publisherYear = publisherYear;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public LocalDate getPublisherYear() {
        return publisherYear;
    }

    public void setPublisherYear(LocalDate publisherYear) {
        this.publisherYear = publisherYear;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authorName='" + authorName + '\'' +
                ", publisherYear=" + publisherYear +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && Objects.equals(name, book.name) && Objects.equals(authorName, book.authorName) && Objects.equals(publisherYear, book.publisherYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, authorName, publisherYear);
    }
}
