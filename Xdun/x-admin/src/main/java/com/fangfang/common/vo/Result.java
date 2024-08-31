package com.fangfang.common.vo;
//vo:value object 值对象

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//生成数据的getter/setter方法
@NoArgsConstructor
//无参构造
@AllArgsConstructor
//全参构造
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success() {
        return new Result<T>(20000, "success", null);
    }
    public static <T> Result<T> success(T data) {
        return new Result<T>(20000, "success", data);
    }
    public static <T> Result<T> success(String message) {
        return new Result<T>(20000, message, null);
    }
    public static <T> Result<T> success(String message, T data) {
        return new Result<T>(20000, message, data);
    }

    public static <T> Result<T> fail() {
        return new Result<T>(20001, "fail", null);
    }
    public static <T> Result<T> fail(String message) {
        return new Result<T>(20001, message, null);
    }
    public static <T> Result<T> fail(Integer code) {
        return new Result<T>(code, "fail", null);
    }
    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<T>(code, message, null);
    }
}
