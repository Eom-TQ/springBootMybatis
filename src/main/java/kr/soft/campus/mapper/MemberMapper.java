package kr.soft.campus.mapper;


import kr.soft.campus.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberMapper {
    //@Select(" SELECT idx, id, name, password, email, created_at as createdAt FROM members")
    List<Member> getAllMembers();

    //@Select("SELECT id, name, password, email, created_at as createdAt FROM members WHERE id = #{id}")
    Member findMemberById(String id);


}