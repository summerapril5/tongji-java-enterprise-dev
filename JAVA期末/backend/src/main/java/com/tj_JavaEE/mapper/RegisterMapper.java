package com.tj_JavaEE.mapper;

import com.tj_JavaEE.entity.Admin;
import com.tj_JavaEE.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RegisterMapper {

    //依据账号查询用户
    @Select("select * from user where account=#{account}")
    User findUserByAccount(String account);

    @Insert("insert into user(nickname,avatar_url,account,password_hash,email) values(#{nickname},#{avatarUrl},#{account},#{password},#{email})")
    void userAdd(User user);

    @Select("select * from admin where account=#{account}")
    Admin findAdminByAccount(String account);

    @Insert("insert into admin(account,password_hash) values(#{account},#{password})")
    void adminAdd(Admin admin);

}
