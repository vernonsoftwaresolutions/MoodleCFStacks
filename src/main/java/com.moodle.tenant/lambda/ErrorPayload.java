package com.moodle.tenant.lambda;

/**
 * Created by andrewlarsen on 8/26/17.
 */
public class ErrorPayload {

    private int code;
    private String message;

    public ErrorPayload() {
    }

    public ErrorPayload(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
