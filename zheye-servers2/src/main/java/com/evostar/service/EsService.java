package com.evostar.service;

import com.evostar.dao.EsDAO;
import com.evostar.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EsService {
    @Autowired
    private EsDAO esDAO;
    @Autowired
    @Qualifier("elasticsearchTemplate")
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public List<Question> getQuestionByTitle(String search){
        return esDAO.findQuestionsByTitleContaining(search);
    }

    public void addQuestion(Question question){
        elasticsearchRestTemplate.save(question);
    }
}

