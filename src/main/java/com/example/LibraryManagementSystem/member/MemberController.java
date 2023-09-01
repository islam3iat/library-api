package com.example.LibraryManagementSystem.member;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/members")
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public List<Member> getMembers(){
        return memberService.getMembers();
    }
    @GetMapping("{member_id}")
    public Member getMember(@PathVariable("member_id") int id){
        return memberService.getMember(id);
    }
    @PostMapping
    public void addMember(@RequestBody MemberDto memberDto){
        memberService.addMember(memberDto);
    }
    @DeleteMapping("{member_id}")
    public void removeMember(@PathVariable("member_id") int id){
        memberService.removeMember(id);
    }
    @PutMapping("{member_id}")
    public void updateMember(@PathVariable("member_id") int id,@RequestBody MemberDto memberDto){
        memberService.updateMember(id, memberDto);
    }
}
