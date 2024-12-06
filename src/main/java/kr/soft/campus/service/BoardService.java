package kr.soft.campus.service;

import kr.soft.campus.domain.Board;
import kr.soft.campus.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

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
}
