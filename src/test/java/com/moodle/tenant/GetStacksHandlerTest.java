package com.moodle.tenant;

import com.amazonaws.services.cloudformation.model.Stack;
import com.amazonaws.services.cloudformation.model.Tag;
import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodle.tenant.cloudformation.CloudformationClient;
import com.moodle.tenant.factory.ProxyResponseFactory;
import com.moodle.tenant.lambda.ProxyRequest;
import com.moodle.tenant.lambda.ProxyResponse;
import com.moodle.tenant.model.CFStack;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
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
    @Mock
    private CloudformationClient client;

    private GetStacksHandler handler;
    private Tag tag;
    private ProxyResponse proxyResponse;
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<Stack> stacks;
    @Before
    public void setup() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        tag = new Tag()
                .withKey("tagKey")
                .withValue("tagValue");

        handler = new GetStacksHandler(factory, client);

        proxyResponse = new ProxyResponse();
        stacks = new ArrayList<Stack>(){{
           add(new Stack().withTags(tag));
        }};
        proxyResponse.setBody(objectMapper.writeValueAsString(stacks));

    }
    @Test
    public void handleRequest() throws Exception {

        given(proxyRequest.getQueryParam("tagKey")).willReturn(Optional.of("TYPE"));
        given(proxyRequest.getQueryParam("tagValue")).willReturn(Optional.of("TENANT"));

        given(factory.createResponse(anyObject(), eq(HttpStatus.SC_OK), eq(null))).willReturn(proxyResponse);
        given(client.getStacks(tag)).willReturn(stacks);
        ProxyResponse response = handler.handleRequest(proxyRequest,context );

        assertThat(response, is(proxyResponse));

    }
    @Test
    public void handleRequestClientReturnsEmptyList() throws Exception {

        given(proxyRequest.getQueryParam("tagKey")).willReturn(Optional.of("TYPE"));
        given(proxyRequest.getQueryParam("tagValue")).willReturn(Optional.of("TENANT"));

        given(factory.createResponse(anyObject(), eq(HttpStatus.SC_OK), eq(null))).willReturn(proxyResponse);
        given(client.getStacks(tag)).willReturn(new ArrayList<>());
        ProxyResponse response = handler.handleRequest(proxyRequest,context );

        assertThat(response, is(proxyResponse));

    }
    @Test
    public void handleRequestClientReturnsNull() throws Exception {

        given(proxyRequest.getQueryParam("tagKey")).willReturn(Optional.of("TYPE"));
        given(proxyRequest.getQueryParam("tagValue")).willReturn(Optional.of("TENANT"));

        given(factory.createResponse(anyObject(), eq(HttpStatus.SC_OK), eq(null))).willReturn(proxyResponse);
        given(client.getStacks(tag)).willReturn(null);
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