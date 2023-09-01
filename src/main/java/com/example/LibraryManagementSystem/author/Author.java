package com.example.LibraryManagementSystem.author;

import com.example.LibraryManagementSystem.book.Book;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity(name = "Author")
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(name = "first_name",columnDefinition = "VARCHAR(50)",nullable = false)
    private String firstName;
    @Column(name = "last_name",columnDefinition = "VARCHAR(50)",nullable = false)
    private String lastName;
    @Column(name = "date_of_brith",columnDefinition = "DATE")
    private Date dateOfBrith;
    @OneToMany(mappedBy = "author",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST,CascadeType.REMOVE}
    )
    private List<Book> books=new ArrayList<>();

    public Author(int id, String firstName, String lastName, Date dateOfBrith) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBrith = dateOfBrith;
    }

    public Author(String firstName, String lastName, Date dateOfBrith) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBrith = dateOfBrith;
    }

    public Author() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBrith() {
        return dateOfBrith;
    }

    public void setDateOfBrith(Date dateOfBrith) {
        this.dateOfBrith = dateOfBrith;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBrith=" + dateOfBrith +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id && Objects.equals(firstName, author.firstName) && Objects.equals(lastName, author.lastName) && Objects.equals(dateOfBrith, author.dateOfBrith);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, dateOfBrith);
    }
}
