package kr.soft.campus.mapper;

import kr.soft.campus.domain.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BoardMapper {

  //@Select("SELECT idx, title, content, deleteYn, good, created, modified, createdBy, modifiedBy from board where deleteYn = 'N' ")
  List<Board> selectAllBoard();
 // @Select("SELECT idx, title, content, deleteYn, good, created, modified, createdBy, modifiedBy from board where idx = #{idx}")
  Board findById(int idx);

 // @Update("UPDATE board set good = good+1 where idx = #{idx}")
  void boardGoodUp(int idx);

//@Update("UPDATE board set title = #{title}, content = #{content}, good = #{good}, modified = #{modified}, modifiedBy = #{modifiedBy} where idx  = #{idx}")
  void update(Board board);

  //@Update("Update board set deleteYn='Y' where idx =#{boardIdx}")
  void delete(int boardIdx);

  void save(Board board);
}
