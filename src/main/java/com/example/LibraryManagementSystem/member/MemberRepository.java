package com.example.LibraryManagementSystem.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    boolean existsMemberByEmail(String email);
    boolean existsMemberById(int id);

}