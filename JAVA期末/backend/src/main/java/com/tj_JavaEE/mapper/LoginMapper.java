package com.tj_JavaEE.mapper;

import com.tj_JavaEE.entity.Admin;
import com.tj_JavaEE.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {

    @Select("select * from user where account = #{account} and password_hash = #{password}")
    User getByAccountAndPassword(User user);


    @Results({
            @Result(column = "password_hash",property = "password"),
    })
    @Select("select * from user where account = #{account}")
    User findUserByAccount(User user);

    @Results({
            @Result(column = "password_hash",property = "password"),
    })
    @Select("select * from admin where account = #{account}")
    Admin findAdminByAccount(Admin admin);



}
