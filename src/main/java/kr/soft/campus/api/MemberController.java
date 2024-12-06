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
        logger.info("find: {}", findIdReq.getId());
        Member m = memberService.findMemberById(findIdReq.getId());
        if(m == null) {
            data.setData("Y");
        } else if( m != null) {
            data.setData("N");
            data.setMsg("no regist");
        }
        return ResponseEntity.ok(data);
    }

    @Data
    static class FindIdReq {
        private String id;
    }

    @Data
    static class MemberListRes {
        private long idx;
        private String id;
        private String name;
        private String email;
        public MemberListRes(Member member) {
            this.idx = member.getIdx();
            this.id = member.getId();
            this.name = member.getName();
            this.email = member.getEmail();
        }
    }
}