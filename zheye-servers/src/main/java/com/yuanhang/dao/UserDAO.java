package com.yuanhang.dao;

import com.yuanhang.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author 肖远航
 * @Repository 注解，它用于将数据访问层 (DAO 层 ) 的类标识为 Spring Bean
 * @Mapper注解的的作用
 *
 * 1:为了把mapper这个DAO交給Spring管理
 * http://412887952-qq-com.iteye.com/blog/2392672
 * 2:为了不再写mapper映射文件
 * https://blog.csdn.net/weixin_39666581/article/details/103899495
 * 3:为了给mapper接口 自动根据一个添加@Mapper注解的接口生成一个实现类
 * http://www.tianshouzhi.com/api/tutorials/mapstruct/292
 */
@Repository
@Mapper
public interface UserDAO {
    /***
     * pom引入mybatis提供的select注解，复杂的查询在resource的mapper中编写，简单的直接用注解实现
     * #{} 为参数占位符 ?，即sql 预编译
     * ${} 为字符串替换，即 sql 拼接
     */
     @Select("select * from user where username= #{username}")
     public User selectByName(String username);

     @Insert("insert into user " +
             "(username,password,salt,head_url) values (#{username},#{password},#{salt},#{headUrl})")
     public int addUser(User user);

}
