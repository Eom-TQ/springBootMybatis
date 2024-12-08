package kr.soft.campus.api;


import kr.soft.campus.domain.Member;
import kr.soft.campus.service.MemberService;
import kr.soft.campus.util.ResponseData;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api/member")
public class MemberController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MemberService memberService;

    public MemberController(MemberService userService) {
        this.memberService = userService;
    }

    @PostMapping("/list")
    public ResponseEntity<?> getAllUsers() {
        ResponseData data = new ResponseData();
        List<Member> members = memberService.getAllUsers();
        List<MemberListRes> list = members.stream()
                .map(m -> new MemberListRes(m))
                .collect(toList());

        data.setData(list);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/findId")
    public ResponseEntity<?> getUserById(@RequestBody FindIdReq findIdReq) {
        ResponseData data = new ResponseData();
        logger.info("find: {}", findIdReq.getUserId());
        Member m = memberService.findMemberById(findIdReq.getUserId());
        if(m == null) {
            data.setData("Y");
        } else if( m != null) {
            data.setData("N");
            data.setMsg("no regist");
        }
        return ResponseEntity.ok(data);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Member member) {

        ResponseData data = new ResponseData();
        logger.info("login: {}", member);
        if(memberService.login(member))  {
            data.setData("Y");
        } else {
            data.setData("N");
        }

        return ResponseEntity.ok(data);
    }

    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody MemberRegistReq memberRegistReq) {
        ResponseData data = new ResponseData();
        logger.info("regist: {}", memberRegistReq);

        Member m = memberService.findMemberById(memberRegistReq.getUserId());

        if(m == null) {
            Member member = memberRegistReq.getMember();
            memberService.registeMember(member);
            data.setMsg("success");
            data.setData("생성됨");
        } else {
            data.setCode("500");
            data.setMsg("fail");
            data.setData("fail");
        }

        return ResponseEntity.ok(data);
    }

    @Data
    static class MemberRegistReq {
        private String userId;
        private String userPw;
        private String userName;
        private String email;

        public Member getMember() {
            Member member = new Member();
            member.setUserId(userId);
            member.setUserPw(userPw);
            member.setName(userName);
            member.setEmail(email);
            return member;
        }
    }

    @Data
    static class FindIdReq {
        private String userId;
    }

    @Data
    static class MemberListRes {
        private long idx;
        private String userId;
        private String name;
        private String email;
        public MemberListRes(Member member) {
            this.idx = member.getIdx();
            this.userId = member.getUserId();
            this.name = member.getName();
            this.email = member.getEmail();
        }
    }
}
