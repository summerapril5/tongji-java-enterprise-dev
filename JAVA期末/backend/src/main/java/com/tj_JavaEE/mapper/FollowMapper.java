package com.tj_JavaEE.mapper;

import com.tj_JavaEE.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FollowMapper {

    @Select("SELECT u.user_id AS userId, u.nickname AS nickname, u.email AS email " +
            "FROM user_follow uf " +
            "JOIN user u ON uf.follow_user_id = u.user_id " +
            "WHERE uf.user_id = #{userId}")
    List<User> selectFollowsByUserId(@Param("userId") long userId);
}
