package com.baizhi.dao;

import com.baizhi.entity.User;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDAO extends Mapper<User> {

    @Results(id = "userResult", value = {
        @Result(property = "id", column = "id", id = true),
        @Result(property = "username", column = "username"),
        @Result(property = "password", column = "password")
    })
    @Select("select * from cmfz_user where id = #{id}")
    User selectUserById(String id);



   /* @Results(id = "userResults", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password")
    })*/
    @Select("select * from cmfz_user")
    List<User> selectSome();



    /*
    	这个一个接口，但不需要实现它，用于 函数与SQL语句 的映射
     * */

    @Insert("insert into cmfz_user(name,sex) values(#{name},#{sex})")
    void insertUser(User user);

    @Delete("delete from cmfz_user where id=#{id}")
    void deleteUserById(String id);

    @Update("update cmfz_user set name=#{name},sex=#{sex} where id=#{id}")
    void updateUser(User user);

    @Select("select * from cmfz_user where id=#{id}")
    User selectUserId(int id);

    @Select("select * from cmfz_user")
    List<User> selectAllUsers();

}
