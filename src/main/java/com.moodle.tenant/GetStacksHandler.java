package com.moodle.tenant;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodle.tenant.factory.ProxyResponseFactory;
import com.moodle.tenant.lambda.ProxyRequest;
import com.moodle.tenant.lambda.ProxyResponse;
import com.moodle.tenant.model.CFStack;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;

import java.util.Optional;

/**
 * Created by andrewlarsen on 8/24/17.
 */
public class GetStacksHandler implements RequestHandler<ProxyRequest, ProxyResponse> {
    private final Logger log = Logger.getLogger(this.getClass().getName());

    private ProxyResponseFactory factory;

    private final String TAG_KEY_PARAM = "tagKey";
    private final String TAG_VALUE_PARAM = "tagValue";


    public GetStacksHandler() {
        ObjectMapper mapper = new ObjectMapper();
        this.factory = new ProxyResponseFactory(mapper);
    }

    public GetStacksHandler(ProxyResponseFactory factory) {
        this.factory = factory;
    }

    @Override
    public ProxyResponse handleRequest(ProxyRequest proxyRequest, Context context) {
        log.info("Received request " + proxyRequest);

        Optional<String> tagKey = proxyRequest.getQueryParam(TAG_KEY_PARAM);
        Optional<String> tagValue = proxyRequest.getQueryParam(TAG_VALUE_PARAM);

        if(tagKey.isPresent() && tagValue.isPresent()) {
            log.info("Received tag " + tagKey);

            CFStack stack = new CFStack();
            stack.setTagKey(tagKey.get());
            stack.setTagValue(tagValue.get());

            ProxyResponse proxyResponse = factory.createResponse(stack, HttpStatus.SC_ACCEPTED, null);

            log.info("About to return proxy response  " + proxyResponse);

            return proxyResponse;
        }
        else {
            return factory.createErrorResponse(400, 400, "Bad request", null);
        }
    }

}
