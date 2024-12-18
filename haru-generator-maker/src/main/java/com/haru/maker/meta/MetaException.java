package com.haru.maker.meta;

/**
 * @program: haru-generator-maker
 * @ClassName MetaException
 * @description: 用于抛出Meta信息校验错误的异常
 * @author: HaruLee
 * @createtime: 2024/12/18 18:54
 * @Version 1.0
 **/
public class MetaException extends RuntimeException {

    public MetaException(String message) {
        super(message);
    }

    public MetaException(String message, Throwable cause) {
        super(message, cause);
    }
}
