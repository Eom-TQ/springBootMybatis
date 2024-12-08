package kr.soft.campus.service;

import kr.soft.campus.domain.Member;
import kr.soft.campus.mapper.MemberMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private final MemberMapper memberMapper;

    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public List<Member> getAllUsers() {
        return memberMapper.getAllMembers();
    }

    public Member findMemberById(String userId) {
        Member results = memberMapper.findMemberById(userId);
        return results == null ? null : results;
    }

    public boolean login(Member member) {
        Member member1 = memberMapper.findMemberById(member.getUserId());
        if(member1 != null && member1.getUserPw().equals(member.getUserPw())) {
            return true;
        }
        return false;
    }

    public void registeMember(Member member) {
        memberMapper.regist(member);
    }
}