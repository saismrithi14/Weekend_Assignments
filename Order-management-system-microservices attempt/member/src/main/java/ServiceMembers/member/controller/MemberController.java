package ServiceMembers.member.controller;

import ServiceMembers.member.dto.addMemberDTO;
import ServiceMembers.member.dto.getMemberDTO;
import ServiceMembers.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/oms/members")
public class MemberController {
    @Autowired
    MemberService memberService;
    @PostMapping("/create")
    public ResponseEntity<String> addMember(@RequestBody @Valid addMemberDTO dto)
    {
        return memberService.addMember(dto);
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

    @GetMapping("/exists/{memberId}")
    public boolean containsMember(@PathVariable UUID memberId)
    {
        return memberService.containsMember(memberId);
    }


}
