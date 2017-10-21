package com.moodle.tenant;

import com.amazonaws.services.lambda.runtime.Context;
import com.moodle.tenant.cloudformation.CloudformationClient;
import com.moodle.tenant.factory.ProxyResponseFactory;
import com.moodle.tenant.lambda.ProxyRequest;
import com.moodle.tenant.lambda.ProxyResponse;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;

/**
 * Created by andrewlarsen on 10/20/17.
 */
public class DeleteStackHandlerTest {
    @Mock
    private ProxyResponseFactory factory;
    @Mock
    private CloudformationClient client;
    @Mock
    private ProxyRequest proxyRequest;
    @Mock
    private Context context;
    private Map<String, String> cors;
    private ProxyResponse proxyResponse;

    DeleteStackHandler handler;
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        cors = new HashMap<String, String>(){{
            put("Access-Control-Allow-Origin", "*");
        }};
        proxyResponse = new ProxyResponse();
        handler = new DeleteStackHandler(factory,client);

    }
    @Test
    public void handleRequest() throws Exception {
        given(proxyRequest.getPathParam("stackname")).willReturn(Optional.of("stackname"));
        given(factory.createResponse(anyObject(), eq(HttpStatus.SC_OK), eq(cors))).willReturn(proxyResponse);

        ProxyResponse response = handler.handleRequest(proxyRequest,context );
        assertThat(response, is(proxyResponse));

    }
    @Test
    public void handleRequestBadRequest() throws Exception {
        given(proxyRequest.getPathParam("stackname")).willReturn(Optional.empty());
        given(factory.createErrorResponse(400, 400,"Bad request", cors)).willReturn(proxyResponse);

        ProxyResponse response = handler.handleRequest(proxyRequest,context );
        assertThat(response, is(proxyResponse));

    }
    @Test
    public void handleRequestInternalServerError() throws Exception {
        given(proxyRequest.getPathParam("stackname")).willReturn(Optional.of("stackname"));
        given(client.deleteStack("stackname")).willThrow(new RuntimeException("ERROR"));
        given(factory.createErrorResponse(500, 500,"Internal Server Error", cors)).willReturn(proxyResponse);

        ProxyResponse response = handler.handleRequest(proxyRequest,context );
        assertThat(response, is(proxyResponse));

    }

}