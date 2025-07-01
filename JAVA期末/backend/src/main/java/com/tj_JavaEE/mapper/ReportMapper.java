package com.tj_JavaEE.mapper;

import com.tj_JavaEE.dto.PostComplainInfo;
import com.tj_JavaEE.dto.UserComplainInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ReportMapper {
    @Select("select ur.report_id as reportId, ur.report_reason as reportReason, u1.user_id as reporterId, u1.nickname as reporterName, u2.user_id as reportedUserId, u2.nickname as reportedUserName, ur.report_time as reportTime from user_report as ur inner join user u1 on ur.reporter_id = u1.user_id inner join user u2 on ur.reported_user_id = u2.user_id where ur.status = 'pending'")
    List<UserComplainInfo> userComplainInfoList();

    @Update("UPDATE user_report SET status = 'reviewed' WHERE report_id = #{reportId}")
    void userComplainProcess(@Param("reportId") Long reportId);

    @Select("select pr.report_id as reportId, pr.report_reason as reportReason, u.user_id as reporterId, u.nickname as reporterName, p.post_id as reportedPostId, p.post_title as postTitle, pr.report_time as reportTime from post_report as pr inner join user u on pr.reporter_id = u.user_id inner join post p on pr.reported_post_id = p.post_id where pr.status = 'pending'")
    List<PostComplainInfo> postComplainInfoList();

    @Update("UPDATE post_report SET status = 'reviewed' WHERE report_id = #{reportId}")
    void postComplainProcess(@Param("reportId") Long reportId);
}
