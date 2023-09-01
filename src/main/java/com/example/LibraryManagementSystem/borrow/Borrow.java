package com.example.LibraryManagementSystem.borrow;

import com.example.LibraryManagementSystem.book.Book;
import jakarta.persistence.*;
import com.example.LibraryManagementSystem.member.Member;
import lombok.Getter;

import java.sql.Date;
import java.util.Objects;

@Getter
@Entity(name = "Borrow")
@Table(name = "borrow")
public class Borrow {
    @EmbeddedId
    private BorrowId id;
    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id",foreignKey =@ForeignKey(name = "borrow_book_id"))
    private Book book;
    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id",foreignKey = @ForeignKey(name = "borrow_member_id"))
    private Member member;
    @Column(name ="borrow_date",columnDefinition = "DATE",nullable = false)
    private Date borrowDate;
    @Column(name ="return_date",columnDefinition = "DATE",nullable = false)

    private Date returnDate;

    public Borrow(BorrowId id, Book book, Member member, Date borrowDate, Date returnDate) {
        this.id = id;
        this.book = book;
        this.member = member;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public Borrow() {
    }

    public void setId(BorrowId id) {
        this.id = id;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Borrow borrow = (Borrow) o;
        return Objects.equals(id, borrow.id) && Objects.equals(book, borrow.book) && Objects.equals(member, borrow.member) && Objects.equals(borrowDate, borrow.borrowDate) && Objects.equals(returnDate, borrow.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, member, borrowDate, returnDate);
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "id=" + id +
                ", book=" + book +
                ", member=" + member +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
