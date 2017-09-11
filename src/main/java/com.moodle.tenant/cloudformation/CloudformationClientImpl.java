package com.moodle.tenant.cloudformation;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.model.Stack;
import com.amazonaws.services.cloudformation.model.Tag;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by andrewlarsen on 9/10/17.
 */
public class CloudformationClientImpl implements CloudformationClient {
    private final Logger log = Logger.getLogger(this.getClass().getName());

    private AmazonCloudFormation cloudFormation;

    public CloudformationClientImpl(AmazonCloudFormation cloudFormation) {
        this.cloudFormation = cloudFormation;
    }

    @Override
    public List<Stack> getStacks(Tag tag) {
        log.info("About to get stacks with tag " + tag);
        return cloudFormation.describeStacks()
                .getStacks()
                .stream()
                .filter(stack -> stack.getTags().contains(tag))
                .collect(Collectors.toList());
    }
}
