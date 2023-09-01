package com.example.LibraryManagementSystem.member;

import com.example.LibraryManagementSystem.book.Book;
import com.example.LibraryManagementSystem.borrow.Borrow;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Member")
@Table(name = "member",uniqueConstraints = {@UniqueConstraint(name = "member_unique_email",columnNames = "email")})
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "first_name",columnDefinition = "VARCHAR(50)",nullable = false)
    private String firstName;
    @Column(name = "last_name",columnDefinition = "VARCHAR(50)",nullable = false)

    private String lastName;
    @Column(name = "email",columnDefinition = "VARCHAR(255)",nullable = false)
    private String email;
    @Column(name = "phone",columnDefinition = "VARCHAR(30)")
    private String phone;

//    @OneToMany(
//            cascade = {CascadeType.PERSIST,CascadeType.REMOVE},
//            mappedBy = "member"
//    )
//
//    private List<Borrow> borrows=new ArrayList<>();
    public Member(int id, String firstName, String lastName, String email, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public Member() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return id == member.id && Objects.equals(firstName, member.firstName) && Objects.equals(lastName, member.lastName) && Objects.equals(email, member.email) && Objects.equals(phone, member.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phone);
    }
}
