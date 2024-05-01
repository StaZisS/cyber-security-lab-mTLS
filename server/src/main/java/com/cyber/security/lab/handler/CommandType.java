package com.cyber.security.lab.handler;

import com.cyber.security.lab.ResponseTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandType {
    ResponseTypeEnum value();
}
