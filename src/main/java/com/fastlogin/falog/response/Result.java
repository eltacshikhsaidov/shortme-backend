package com.fastlogin.falog.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(
                Response.SUCCESS.getCode(),
                Response.SUCCESS.getMessage(),
                data
        );
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static Result<?> failed(Integer code, String msg) {
        return new Result<>(
                code,
                msg,
                null
        );
    }
}
