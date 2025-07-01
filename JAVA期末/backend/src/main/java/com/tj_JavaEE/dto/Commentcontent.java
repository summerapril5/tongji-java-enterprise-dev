package com.tj_JavaEE.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Commentcontent {

    private String content;

    private long commenterId;

    private long postId;

    private String commentTime;





}
