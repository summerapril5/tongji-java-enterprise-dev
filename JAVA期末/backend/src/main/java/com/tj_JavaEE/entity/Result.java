package com.tj_JavaEE.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    //创建Result类
    private int code;
    private String msg;
    private Object data;

    public static Result success(Object data) {
        return new Result(200, "success", data);
    }

    public static Result success() {
        return new Result(200, "success", null);
    }

    public static Result success(Integer code,Object data) {
        return new Result(code, "success", data);
    }

    public static Result success(Integer code,String msg,Object data) {
        return new Result(code, msg, data);
    }


    public static Result error(String msg) {
        return new Result(400, msg, null);
    }

    public static Result error(Integer code,String msg,Object data) {
        return new Result(code, msg, data);
    }

    public static Result error(Integer code ,String msg) {
        return new Result(code, msg, null);
    }


}
