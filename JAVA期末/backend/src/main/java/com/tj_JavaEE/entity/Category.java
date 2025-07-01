package com.tj_JavaEE.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @TableId(type = com.baomidou.mybatisplus.annotation.IdType.AUTO)
    @TableField("category_id")
    private Long categoryId;

    @TableField("category_name")
    private String categoryName;

    @TableField("category_description")
    private String categoryDescription;

    @TableField("category_image_url")
    private String categoryImageUrl;
}
