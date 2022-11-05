package com.fastlogin.falog.response;

import lombok.Data;

@Data
public class Response {
    private String shortUrl;
    private RespStatus status;
}
