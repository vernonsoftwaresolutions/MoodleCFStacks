package com.moodle.tenant;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodle.tenant.factory.ProxyResponseFactory;
import com.moodle.tenant.lambda.LambdaHelper;
import com.moodle.tenant.lambda.ProxyRequest;
import com.moodle.tenant.lambda.ProxyResponse;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;

import java.util.Optional;


/**
 * Created by andrewlarsen on 10/20/17.
 */
public class DeleteStackHandler implements RequestHandler<ProxyRequest, ProxyResponse> {
    private final Logger log = Logger.getLogger(this.getClass().getName());

    private ProxyResponseFactory factory;

    private final String STACK_NAME_PARAM = "stackname";

    public DeleteStackHandler(ProxyResponseFactory factory) {
        this.factory = factory;
    }

    public DeleteStackHandler() {
        this.factory = new ProxyResponseFactory(new ObjectMapper());

    }

    @Override
    public ProxyResponse handleRequest(ProxyRequest input, Context context) {
        log.info("Received request " + input);
        Optional<String> stackName = input.getQueryParam(STACK_NAME_PARAM);

        if(stackName.isPresent()){
            ProxyResponse proxyResponse = factory.createResponse(null, HttpStatus.SC_OK, LambdaHelper.getCorsHeaders());

            log.info("About to return proxy response  " + proxyResponse);

            return proxyResponse;
        }
        else {
            log.info("Received bad request");
            return factory.createErrorResponse(400, 400, "Bad request", LambdaHelper.getCorsHeaders());
        }

    }
}
