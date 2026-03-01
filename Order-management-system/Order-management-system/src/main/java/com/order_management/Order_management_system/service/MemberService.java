package com.order_management.Order_management_system.service;

import com.order_management.Order_management_system.dto.*;
import com.order_management.Order_management_system.exception.*;
import com.order_management.Order_management_system.model.Member;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.*;
import java.util.concurrent.ConcurrentHashMap;


@Service

public class MemberService {
    private ConcurrentHashMap<UUID, Member> members = new ConcurrentHashMap<>();


    public void addMember(addMemberDTO dto)
    {
        Member new_member = new Member();
        new_member.setMemberId(UUID.randomUUID());
        new_member.setFullName(dto.getFullName());
        new_member.setEmail(dto.getEmail());
        new_member.setPassword(dto.getPassword());
        members.put(new_member.getMemberId(), new_member);
    }

    public List<getMemberDTO> getAllMembers()
    {
        List<getMemberDTO> memberDTOList = new ArrayList<>();
        members.forEach((key,value)-> memberDTOList.add(mapEntityToDTO(value)));
        return memberDTOList;
    }

    public void deleteMemberById(UUID id)
    {
        Member member = members.remove(id);
        if(member == null)
        {
            throw new MemberNotFoundException("The member with the id " + id + " does not exist");
        }

    }

    public getMemberDTO getMemberById(UUID id)
    {
        Member member = members.get(id);
        if(member == null)
        {
            throw new MemberNotFoundException("Member with id " + id + " doesn't exist");
        }
        getMemberDTO dto = new getMemberDTO();
        dto.setMemberId(member.getMemberId());
        dto.setFullName(member.getFullName());
        dto.setEmail(member.getEmail());

        return dto;

    }

    private getMemberDTO mapEntityToDTO(Member member)
    {
        getMemberDTO dto = new getMemberDTO();
        dto.setMemberId(member.getMemberId());
        dto.setFullName(member.getFullName());
        dto.setEmail(member.getEmail());
        return dto;
    }

    public boolean containsMember(UUID memberId)
    {
        return members.containsKey(memberId);
    }
}
