package com.fastlogin.falog.response;

import lombok.*;

@Getter
@AllArgsConstructor
public enum Response {
    SUCCESS(1, "success"), ERROR(2, "error");

    private final Integer code;
    private final String message;
}
