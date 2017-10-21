package com.moodle.tenant.cloudformation;

import com.amazonaws.services.cloudformation.model.DeleteStackResult;
import com.amazonaws.services.cloudformation.model.Stack;
import com.amazonaws.services.cloudformation.model.Tag;

import java.util.List;

/**
 * Created by andrewlarsen on 9/10/17.
 */
public interface CloudformationClient {

    /**
     * Method to retrieve a list of all stacks with a given tag
     * @param tag
     * @return
     */
    List<Stack> getStacks(Tag tag);

    /**
     * Method to delete a stack based on a given stack name
     * @param stackName
     */
    DeleteStackResult deleteStack(String stackName);
}
