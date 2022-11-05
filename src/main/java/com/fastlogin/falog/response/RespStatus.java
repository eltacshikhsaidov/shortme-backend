package com.fastlogin.falog.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class RespStatus implements Serializable {

    private Integer statusCode;
    private String statusMessage;

    private static final Integer SUCCESS_CODE = 1;
    private static final String SUCCESS_MESSAGE = "success";


    public static RespStatus getSuccessMessage() {
        return new RespStatus(SUCCESS_CODE, SUCCESS_MESSAGE);
    }


}
