package com.evostar.dao;

import com.evostar.model.Answer;
import com.evostar.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import javax.annotation.ManagedBean;
import java.util.List;

@Repository
@Mapper
public interface CommentDAO {
    @Select({"select * from answer where id=#{id}"})
    public Comment getById(int id);

    @Insert({"insert into comment( content, user_id, entity_id,type,created_date,responded) values (#{content},#{userId},#{entityId},#{type},#{createdDate},#{responded})"})
    public int addComment(Comment comment);

    public List<Comment> getCommentListById(@Param(value = "entityId") int entityId,
                                            @Param(value = "type") int type,
                                            @Param(value = "offset") int offset,
                                            @Param(value = "limit") int limit);

    @Select("select count(*) from comment where entity_id = #{id} and type = #{type}")
    public int getCountByTypeAndId(int id, int type);


    @Select("select count(*) from comment where entity_id = #{aid} and type = 1")
    public int getCommentCountByAnswerId(int aid);
}
