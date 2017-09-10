package com.moodle.tenant.lambda;

import java.util.Map;

/**
 * Created by andrewlarsen on 8/26/17.
 */
public class ProxyResponse {
    private int statusCode;
    private Map<String, String> headers;
    private String body;

    public ProxyResponse() {
    }

    public ProxyResponse(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ProxyResponse{" +
                "statusCode=" + statusCode +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}
