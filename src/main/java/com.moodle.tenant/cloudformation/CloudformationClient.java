package com.moodle.tenant.cloudformation;

import com.amazonaws.services.cloudformation.model.Stack;
import com.amazonaws.services.cloudformation.model.Tag;

import java.util.List;

/**
 * Created by andrewlarsen on 9/10/17.
 */
public interface CloudformationClient {

    List<Stack> getStacks(Tag tag);
}
