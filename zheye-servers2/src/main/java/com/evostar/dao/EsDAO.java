package com.evostar.dao;

import com.evostar.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EsDAO extends CrudRepository<Question, Integer> {
    public List<Question> findQuestionsByTitleContaining(String search);
}
