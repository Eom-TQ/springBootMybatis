package kr.soft.campus.service;

import kr.soft.campus.domain.Board;
import kr.soft.campus.domain.Member;
import kr.soft.campus.mapper.BoardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BoardService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BoardMapper boardMapper;
    @Autowired
    private MemberService memberService;

    @Transactional
    public List<Board> findSearch(String keyword, String created) {
        List<Board> boards = boardMapper.selectAllBoard();
        boards = search(boards, keyword, created); //seach
        return boards;
    }

    @Transactional
    public List<Board> search(List<Board> boards, String keyword, String created) {
        Stream<Board> stream = boards.stream();

        if (keyword != null && !keyword.isEmpty()) {
            String finalKeyword = keyword.toLowerCase();
            stream = stream.filter(i -> i.getTitle().toLowerCase().contains(finalKeyword));
        }

        if (created != null && !created.isEmpty()) {
            String finalKeyword = created.toLowerCase();
            stream = stream.filter(i -> i.getCreatedBy().toLowerCase().contains(finalKeyword));
        }

        return stream.collect(Collectors.toList());
    }

    @Transactional
    public Board boardDetail(int id) {
        Board b = boardMapper.findById(id);
        return b;
    }

    @Transactional
    public boolean boardGood(int idx) {
        if(boardMapper.findById(idx) == null) {
            return false;
        }

        boardMapper.boardGoodUp(idx);
        return true;
    }

    @Transactional
    public void update(String userId, Board board) {

        //사용자 정보 불러오기
        //Member member = memberService.findMemberById(userId);

        Board detail = boardMapper.findById(board.getIdx());

        board.setGood(detail.getGood());
        board.setCreatedBy(detail.getCreatedBy());  //생성 자
        board.setCreated(detail.getCreated());
        board.setModified(LocalDateTime.now());
        board.setModifiedBy(userId);

        boardMapper.update(board);
    }

    @Transactional
    public boolean remove(int idx) {
        logger.info("remove");

        if(boardMapper.findById(idx) == null) {
            return false;
        }
        boardMapper.delete(idx);

        return true;
    }


}
