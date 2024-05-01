package com.cyber.security.lab.body;

import com.cyber.security.lab.ResponseTypeEnum;
import lombok.Getter;

@Getter
public record RequestBody(
        ResponseTypeEnum typeRequest,
        Object body
) {
}
