package com.order_management.Order_management_system.controller;

import com.order_management.Order_management_system.dto.addMemberDTO;
import com.order_management.Order_management_system.dto.getMemberDTO;
import com.order_management.Order_management_system.service.MemberService;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/oms/member")
public class MemberController {
    @Autowired
    MemberService memberService;

    @PostMapping("/create")
    public void addMember(@RequestBody @Valid addMemberDTO dto)
    {
        memberService.addMember(dto);
    }

    @GetMapping("/all")

    public List<getMemberDTO> getAllMembers()
    {
        return memberService.getAllMembers();
    }

    @DeleteMapping("/{id}")

    public void deleteMemberById(@PathVariable UUID id)
    {
        memberService.deleteMemberById(id);
    }

    @GetMapping("/{id}")
    public getMemberDTO getMemberById(@PathVariable UUID id)
    {
        return memberService.getMemberById(id);
    }


}
