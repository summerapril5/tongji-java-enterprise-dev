package com.tj_JavaEE.mapper;

import com.tj_JavaEE.dto.FeedbackInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FeedbackMapper {
    @Select("select f.feedback_id as feedbackId, f.feedback_content as feedbackContent, u.user_id as feedbackUserId, u.nickname as feedbackUserName, f.feedback_time as feedbackTime from feedback f inner join user u on f.feedback_user_id = u.user_id where f.status = 'pending'")
    List<FeedbackInfo> feedbackInfoList();

    @Update("UPDATE feedback SET status = 'reviewed' WHERE feedback_id = #{feedbackId}")
    void feedbackProcess(@Param("feedbackId") Long feedbackId);
}
