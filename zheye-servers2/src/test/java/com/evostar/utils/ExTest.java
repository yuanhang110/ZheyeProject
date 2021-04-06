package com.evostar.utils;

import com.evostar.ApplicationTests;
import com.evostar.model.Question;
import com.evostar.service.EsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.util.List;


public class ExTest extends ApplicationTests {
    @Qualifier("elasticsearchTemplate")
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private EsService esService;
    @Test
    public void testAddData(){
        Question question = new Question();
        question.setId(1);
        question.setTitle("1111111111");
        elasticsearchRestTemplate.save(question);
    }

    @Test
    public void testFind(){
        List<Question> list = esService.getQuestionByTitle("1111");
        System.out.println(list);
    }
}
