package com.moodle.tenant;

import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import com.amazonaws.services.cloudformation.model.DeleteStackResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodle.tenant.cloudformation.CloudformationClient;
import com.moodle.tenant.cloudformation.CloudformationClientImpl;
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

    private CloudformationClient client;

    private final String STACK_NAME_PARAM = "stackname";


    public DeleteStackHandler(ProxyResponseFactory factory, CloudformationClient client) {
        this.factory = factory;
        this.client = client;
    }

    public DeleteStackHandler() {
        log.info("Creating handler");
        ObjectMapper mapper = new ObjectMapper();
        this.factory = new ProxyResponseFactory(mapper);
        client = new CloudformationClientImpl(AmazonCloudFormationClientBuilder.defaultClient());
        this.factory = new ProxyResponseFactory(mapper);
        log.info("Hanlder created");
    }

    @Override
    public ProxyResponse handleRequest(ProxyRequest input, Context context) {
        log.info("Received request " + input);
        Optional<String> stackName = input.getPathParam(STACK_NAME_PARAM);

        try {


            if (stackName.isPresent()) {

                DeleteStackResult result = client.deleteStack(stackName.get());

                ProxyResponse proxyResponse = factory.createResponse(result,
                        HttpStatus.SC_OK, LambdaHelper.getCorsHeaders());

                log.info("About to return proxy response  " + proxyResponse);

                return proxyResponse;
            } else {
                log.info("Received bad request");
                return factory.createErrorResponse(400, 400, "Bad request", LambdaHelper.getCorsHeaders());
            }

        }
        catch (Exception e){
            log.error("error processing request ", e);
            return factory.createErrorResponse(500, 500, "Internal Server Error", LambdaHelper.getCorsHeaders());

        }
    }
}
