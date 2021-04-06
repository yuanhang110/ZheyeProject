package com.evostar.dao;

import com.evostar.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDAO {
    @Select("select * from user where name = #{username}")
    public User selectByName(String username);

    @Insert("insert into user (name,password,salt,head_url) values (#{name},#{password},#{salt},#{headUrl})")
    public int addUser(User user);


    @Select("select * from user where id = #{id}")
    public User selectById(int id);

    public List<User> getUserListByIds(@Param(value = "userList") List<String> userList);

}
