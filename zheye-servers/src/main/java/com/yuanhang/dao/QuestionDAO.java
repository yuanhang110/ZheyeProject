package com.yuanhang.dao;

import com.yuanhang.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionDAO {
    @Insert({"insert into question (title, content, created_date, user_id, comment_count) " +
            "values(#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    public int addQuestion(Question question);

    @Select({"select * from question where id=#{id}"})
    public Question getById(int questionId);


    List<Question> selectLatestQuestions(@Param("userId") int qIdList, @Param("offset") int offset,
                                         @Param("limit") int limit);

}
