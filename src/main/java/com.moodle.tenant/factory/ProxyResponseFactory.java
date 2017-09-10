package com.moodle.tenant.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodle.tenant.lambda.ErrorPayload;
import com.moodle.tenant.lambda.ProxyRequest;
import com.moodle.tenant.lambda.ProxyResponse;

import java.io.IOException;
import java.util.Map;

/**
 * Created by andrewlarsen on 8/26/17.
 */
public class ProxyResponseFactory {
    private ObjectMapper objectMapper;

    public ProxyResponseFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Method to create response for Lambda method
     * AWS requires the following JSON formate when API Gateway is set to proxy
     * {
     "statusCode": httpStatusCode,
     "headers": { "headerName": "headerValue", ... },
     "body": "..."
     }
     *
     * Note that body is a string value
     * @param body
     * @param headers
     * @param <T>
     * @return
     * @throws JsonProcessingException
     */
    public <T> ProxyResponse createResponse(T body, int status, Map<String, String> headers) {
        ProxyResponse response = new ProxyResponse();

        try {
            String bodyString;
            bodyString = objectMapper.writeValueAsString(body);
            response.setStatusCode(status);
            response.setBody(bodyString);
            response.setHeaders(headers);
            return response;
        }
        catch (JsonProcessingException e){
            response.setBody("InternalError");
            response.setStatusCode(500);
            return response;
        }
    }

    public ProxyResponse createErrorResponse(int status, int errorCode, String message, Map<String, String> headers) {
        return createResponse(new ErrorPayload(errorCode, message), status, headers);
    }




}
