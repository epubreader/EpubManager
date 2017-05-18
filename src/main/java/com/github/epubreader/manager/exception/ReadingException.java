package com.github.epubreader.manager.exception;

/**
 * Created by Yong on 2017/5/18.
 */
public class ReadingException extends RuntimeException {

    private static final long serialVersionUID = -3674458503294310650L;

    public ReadingException(String message) {
        super(message);
    }

    public ReadingException(String message, Throwable throwable) {
        super(message, throwable);
    }

}