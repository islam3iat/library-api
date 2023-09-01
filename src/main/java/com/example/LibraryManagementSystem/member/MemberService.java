package com.example.LibraryManagementSystem.member;

import com.example.LibraryManagementSystem.Exception.ApiRequestException;
import com.example.LibraryManagementSystem.Exception.DuplicateResourceException;
import com.example.LibraryManagementSystem.Exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public void addMember(MemberDto memberDto){
        if(memberRepository.existsMemberByEmail(memberDto.getEmail()))
            throw new DuplicateResourceException("email already taken");
        Member member=new Member();
        member.setFirstName(memberDto.getFirstName());
        member.setLastName(memberDto.getLastName());
        member.setEmail(memberDto.getEmail());
        member.setPhone(memberDto.getPhone());
        memberRepository.save(member);
    }
    public List<Member> getMembers(){
        return memberRepository.findAll();
    }
    public Member getMember(int id){
        return memberRepository.findById(id).orElseThrow(() -> new NotFoundException("member by [%s] not found".formatted(id)));
    }
    public void removeMember(int id){
        if(!memberRepository.existsMemberById(id))
            throw new NotFoundException("member by [%s] not found".formatted(id));
        memberRepository.deleteById(id);
    }
    public void updateMember(int id,MemberDto memberDto){
        if(memberRepository.existsMemberByEmail(memberDto.getEmail()))
            throw new DuplicateResourceException("email already taken");
        Member member=memberRepository.
                findById(id).orElseThrow(() -> new NotFoundException("member by [%s] not found".formatted(id)));
        boolean changes=false;
        if(memberDto.getFirstName()!=null&&!memberDto.getFirstName().equals(member.getFirstName())){
            member.setFirstName(memberDto.getFirstName());
            changes=true;
        }
        if(memberDto.getLastName()!=null&&!memberDto.getLastName().equals(member.getLastName())){
            member.setLastName(memberDto.getLastName());
            changes=true;
        }
        if(memberDto.getEmail()!=null&&!memberDto.getEmail().equals(member.getEmail())){
            member.setEmail(memberDto.getEmail());
            changes=true;
        }
        if(memberDto.getPhone()!=null&&!memberDto.getPhone().equals(member.getPhone())){
            member.setPhone(memberDto.getPhone());
            changes=true;
        }
        if(!changes)
            throw new ApiRequestException("no change has be made");
        memberRepository.save(member);
    }




}
