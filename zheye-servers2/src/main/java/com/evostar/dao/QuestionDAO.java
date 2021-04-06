package com.evostar.dao;

import com.evostar.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionDAO {
    @Insert({"insert into question (title, content, created_date, user_id, comment_count) " +
            "values(#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public int addQuestion(Question question);

    @Select({"select * from question where id=#{id}"})
    public Question getById(int questionId);


    List<Question> selectLatestQuestions(@Param("userId") int qIdList, @Param("offset") int offset,
                                         @Param("limit") int limit);

    List<Question> selectLatestFollowedQuestions(@Param("userList") List<String> userList,
                                                 @Param("offset") int offset,
                                                 @Param("limit") int limit);

}
