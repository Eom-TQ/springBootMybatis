package kr.soft.campus.mapper;

import kr.soft.campus.domain.Board;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BoardMapper {

  @Select("SELECT idx, title, content, deleteYn, good, created, modified, createdBy, modifiedBy from board where deleteYn = 'N' ")
  List<Board> selectAllBoard();
  @Select("SELECT idx, title, content, deleteYn, good, created, modified, createdBy, modifiedBy from board where idx = #{idx}")
  Board findById(int idx);

  @Update("UPDATE board set good = good+1 where idx = #{idx}")
  void boardGoodUp(int idx);
}
