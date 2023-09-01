package com.example.LibraryManagementSystem.borrow;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable

public class BorrowId implements Serializable {
    @Column(name = "course_id")
    private int bookId;
    @Column(name = "member_id")
    private int memberId;

    public BorrowId(int bookId, int memberId) {
        this.bookId = bookId;
        this.memberId = memberId;
    }

    public BorrowId() {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "BorrowId{" +
                "bookId=" + bookId +
                ", memberId=" + memberId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowId borrowId = (BorrowId) o;
        return bookId == borrowId.bookId && memberId == borrowId.memberId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, memberId);
    }
}
