package com.evostar.dao;

import com.evostar.model.Answer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AnswerDAO {
    @Insert({"insert into answer( user_id, question_id, content,created_date) values (#{userId},#{questionId},#{content},#{createdDate})"})
    public int addAnswer(Answer answer);


    public List<Answer> getAnswerListByQidDesc(@Param(value = "id") int qid, @Param(value = "offset") int offset,
                                               @Param("limit") int limit);

    public Answer getLastAnswerByQuestionId(int questionId);

    @Select({"select count(*) from answer where question_id = #{qid}"})
    public int getCountByQid(@Param(value = "qid") int qid);

    @Select({"select * from answer where id=#{id}"})
    public Answer getById(int id);

    @Select("select count(*) from answer where user_id = #{userId}")
    public int getAnswerCountByUserId(int userId);
}
