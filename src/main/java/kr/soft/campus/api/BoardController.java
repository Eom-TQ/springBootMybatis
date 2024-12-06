package kr.soft.campus.api;

import kr.soft.campus.domain.Board;
import kr.soft.campus.service.BoardService;
import kr.soft.campus.util.ResponseData;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/board")
public class BoardController {


    Logger logger = LoggerFactory.getLogger(BoardController.class);

    @Autowired
    private BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity<?> list(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyWord,
            @RequestParam(value = "created", required = false, defaultValue = "") String created
    ) {
        ResponseData responseData = new ResponseData();

        List<BoardListRes> lists = boardService.findSearch(keyWord, created).stream().map(board ->
                        new BoardListRes(board)
                )
                .collect(toList());

        responseData.setData(lists);
        if(lists == null || lists.size() == 0) {
            responseData.setMsg("null");
        }

        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/find")
    public ResponseEntity<?> find(@RequestParam(name = "boardId") int boardId) {
        ResponseData responseData = new ResponseData();
        Board board = boardService.boardDetail(boardId);

        BoardDetailRes detail = new BoardDetailRes(board);
        responseData.setData(detail);


        return ResponseEntity.ok(responseData);

    }

    @PostMapping("/good")
    public ResponseEntity<ResponseData> goodUp(@RequestBody BoardIdReq req) {
        ResponseData responseData = new ResponseData();

        logger.info("req: {}", req.getBoardId());
        if(!boardService.boardGood(req.getBoardId())) {
            responseData.setCode("500");
            responseData.setMsg("null");
        }

        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modify(@RequestBody BoardModifyReq req) {
        ResponseData responseData = new ResponseData();
        Board board = new Board();
        board.setContent(req.getContent());
        board.setTitle(req.getTitle());
        board.setIdx(req.getBoardId());
        boardService.update(req.memberId, board);

        return ResponseEntity.ok(responseData);
    }


    @Data
    static class BoardListRes {
        private int boardIdx;
        private String title;
        private String createdBy;
        private int boardGood;
        private LocalDateTime createdAt;

        BoardListRes(Board board) {
            this.boardIdx = board.getIdx();
            this.title = board.getTitle();
            this.createdBy = board.getCreatedBy();
            this.boardGood = board.getGood();
            this.createdAt = board.getCreated();
        }
    }

    @Data
    static class BoardDetailRes {
        private String title;
        private String content;
        private String memberId;
        private int boardGood;
        private LocalDateTime createdAt;

        BoardDetailRes(Board board) {
            this.title = board.getTitle();
            this.content = board.getContent();
            this.boardGood = board.getGood();
            this.memberId = board.getCreatedBy();
            this.createdAt = board.getCreated();
        }
    }

    @Data
    static class BoardIdReq
    {
        private int boardId;
    }

    @Data
    static class BoardModifyReq {
        private int boardId;
        private String title;
        private String content;
        private String memberId;
    }
}
