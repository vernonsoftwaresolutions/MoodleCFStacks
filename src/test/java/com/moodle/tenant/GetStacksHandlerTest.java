package com.moodle.tenant;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodle.tenant.factory.ProxyResponseFactory;
import com.moodle.tenant.lambda.ProxyRequest;
import com.moodle.tenant.lambda.ProxyResponse;
import com.moodle.tenant.model.CFStack;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;

/**
 * Created by andrewlarsen on 9/10/17.
 */
public class GetStacksHandlerTest {
    @Mock
    private ProxyResponseFactory factory;
    @Mock
    private ProxyRequest proxyRequest;
    @Mock
    private Context context;
    private GetStacksHandler handler;

    private ProxyResponse proxyResponse;
    private ObjectMapper objectMapper = new ObjectMapper();
    private CFStack stack;
    @Before
    public void setup() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        stack = new CFStack();
        stack.setTagKey("tagKey");
        stack.setTagValue("tagValue");

        handler = new GetStacksHandler(factory);

        proxyResponse = new ProxyResponse();
        proxyResponse.setBody(objectMapper.writeValueAsString(stack));

    }
    @Test
    public void handleRequest() throws Exception {

        given(proxyRequest.getQueryParam("tagKey")).willReturn(Optional.of("TYPE"));
        given(proxyRequest.getQueryParam("tagValue")).willReturn(Optional.of("TENANT"));

        given(factory.createResponse(anyObject(), eq(HttpStatus.SC_ACCEPTED), eq(null))).willReturn(proxyResponse);

        ProxyResponse response = handler.handleRequest(proxyRequest,context );

        assertThat(response, is(proxyResponse));

    }
    @Test
    public void handleRequestNoTagKey() throws Exception {

        given(proxyRequest.getQueryParam("tagKey")).willReturn(Optional.empty());
        given(proxyRequest.getQueryParam("tagValue")).willReturn(Optional.of("TENANT"));

        given(factory.createErrorResponse(400, 400, "Bad request", null)).willReturn(proxyResponse);

        ProxyResponse response = handler.handleRequest(proxyRequest,context );

        assertThat(response, is(proxyResponse));

    }
    @Test
    public void handleRequestNoTagValue() throws Exception {

        given(proxyRequest.getQueryParam("tagKey")).willReturn(Optional.of("TYPE"));
        given(proxyRequest.getQueryParam("tagValue")).willReturn(Optional.empty());

        given(factory.createErrorResponse(400, 400, "Bad request", null)).willReturn(proxyResponse);

        ProxyResponse response = handler.handleRequest(proxyRequest,context );

        assertThat(response, is(proxyResponse));

    }
}