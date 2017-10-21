package com.moodle.tenant.lambda;

import java.util.Map;
import java.util.Optional;

/**
 * Created by andrewlarsen on 8/26/17.
 */
public class ProxyRequest {
    private Map<String, String> pathParameters;
    private Map<String, String> queryStringParameters;
    private String body;
    private Map<String, String> headers;

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

    public Map<String, String> getPathParameters() {
        return pathParameters;
    }

    public void setPathParameters(Map<String, String> pathParameters) {
        this.pathParameters = pathParameters;
    }

    public Map<String, String> getQueryStringParameters() {
        return queryStringParameters;
    }

    public void setQueryStringParameters(Map<String, String> queryStringParameters) {
        this.queryStringParameters = queryStringParameters;
    }

    public Optional<String> getQueryParam(String key){
        if (queryStringParameters.containsKey(key)){
            return Optional.of(queryStringParameters.get(key));
        }
        return Optional.empty();
    }

    public Optional<String> getPathParam(String key){
        if (pathParameters.containsKey(key)){
            return Optional.of(pathParameters.get(key));
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "ProxyRequest{" +
                "pathParameters=" + pathParameters +
                ", body='" + body + '\'' +
                ", headers=" + headers +
                ", queryStringParameters=" + queryStringParameters +
                '}';
    }
}
