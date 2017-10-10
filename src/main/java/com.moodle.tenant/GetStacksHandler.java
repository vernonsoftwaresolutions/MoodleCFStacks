package com.moodle.tenant;

import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import com.amazonaws.services.cloudformation.model.Stack;
import com.amazonaws.services.cloudformation.model.Tag;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodle.tenant.cloudformation.CloudformationClient;
import com.moodle.tenant.cloudformation.CloudformationClientImpl;
import com.moodle.tenant.factory.ProxyResponseFactory;
import com.moodle.tenant.lambda.ProxyRequest;
import com.moodle.tenant.lambda.ProxyResponse;
import com.moodle.tenant.model.CFStack;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by andrewlarsen on 8/24/17.
 */
public class GetStacksHandler implements RequestHandler<ProxyRequest, ProxyResponse> {
    private final Logger log = Logger.getLogger(this.getClass().getName());

    private ProxyResponseFactory factory;
    private CloudformationClient client;

    private final String TAG_KEY_PARAM = "tagKey";
    private final String TAG_VALUE_PARAM = "tagValue";


    public GetStacksHandler() {
        ObjectMapper mapper = new ObjectMapper();
        client = new CloudformationClientImpl(AmazonCloudFormationClientBuilder.defaultClient());
        this.factory = new ProxyResponseFactory(mapper);
    }

    public GetStacksHandler(ProxyResponseFactory factory, CloudformationClient client) {
        this.factory = factory;
        this.client = client;
    }

    @Override
    public ProxyResponse handleRequest(ProxyRequest proxyRequest, Context context) {
        log.info("Received request " + proxyRequest);

        Optional<String> tagKey = proxyRequest.getQueryParam(TAG_KEY_PARAM);
        Optional<String> tagValue = proxyRequest.getQueryParam(TAG_VALUE_PARAM);

        if(tagKey.isPresent() && tagValue.isPresent()) {
            log.info("Received tag " + tagKey);

            Tag tag = new Tag()
                    .withKey(tagKey.get())
                    .withValue(tagValue.get());

            log.info("Created new Amazon tag " + tag);

            List<Stack> stacks = client.getStacks(tag);

            List<CFStack> response = stacks.stream().map(stack -> {
                //todo- factor this out to separate class
                String url = stack.getParameters().get(0).getParameterValue() + stack.getParameters().get(1).getParameterValue();
                return new CFStack(stack.getStackName(), url, stack.getStackStatus(), stack.getCreationTime().getTime());
            }).collect(Collectors.toList());

            ProxyResponse proxyResponse = factory.createResponse(response, HttpStatus.SC_OK, getCorsHeaders());

            log.info("About to return proxy response  " + proxyResponse);

            return proxyResponse;
        }
        else {
            log.info("Received bad request");
            return factory.createErrorResponse(400, 400, "Bad request", getCorsHeaders());
        }
    }

    /**
     * Helper method to create cors headers for Browsers
     * @return
     */
    private Map<String, String> getCorsHeaders(){
        Map headers = new HashMap<String, String>();
        headers.put( "Access-Control-Allow-Origin", "*");
        return headers;
    }



}
