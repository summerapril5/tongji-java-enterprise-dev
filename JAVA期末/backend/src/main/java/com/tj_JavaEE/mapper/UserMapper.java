package com.tj_JavaEE.mapper;

import com.tj_JavaEE.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Results({
            @Result(column = "password_hash",property = "password"),
    })
    @Select("select * from user")
    List<User> list();


}
