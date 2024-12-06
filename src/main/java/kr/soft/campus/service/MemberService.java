package kr.soft.campus.service;

import kr.soft.campus.domain.Member;
import kr.soft.campus.mapper.MemberMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private final MemberMapper userMapper;

    public MemberService(MemberMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<Member> getAllUsers() {
        return userMapper.getAllMembers();
    }

    public Member findMemberById(String id) {
        return userMapper.findMemberById(id);
    }
}