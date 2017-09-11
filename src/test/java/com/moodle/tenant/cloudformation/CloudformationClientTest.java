package com.moodle.tenant.cloudformation;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.model.DescribeStacksRequest;
import com.amazonaws.services.cloudformation.model.DescribeStacksResult;
import com.amazonaws.services.cloudformation.model.Stack;
import com.amazonaws.services.cloudformation.model.Tag;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

/**
 * Created by andrewlarsen on 9/10/17.
 */
public class CloudformationClientTest {

    @Mock
    private AmazonCloudFormation cloudFormation;
    private CloudformationClient cloudformationClient;

    private List<Stack> stacks;
    private DescribeStacksResult result;
    private Tag tag1;
    private Tag tag2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        cloudformationClient = new CloudformationClientImpl(cloudFormation);
        tag1 = new Tag()
                .withKey("KEY")
                .withValue("VALUE");
        tag2 = new Tag()
                .withKey("KEY1")
                .withValue("VALUE2");
        stacks = new ArrayList<Stack>() {{
            add(new Stack().withTags(tag1));
            add(new Stack().withTags(tag2));

        }};
        result = new DescribeStacksResult().withStacks(stacks);
    }

    @Test
    public void getStacksTag1Value() throws Exception {

        given(cloudFormation.describeStacks()).willReturn(result);

        List<Stack> result = cloudformationClient.getStacks(tag1);
        assertThat(result.get(0).getTags().size(), is(1));
        assertThat(result.get(0).getTags().get(0).getValue(), is(tag1.getValue()));


    }
    @Test
    public void getStacksTag1Key() throws Exception {

        given(cloudFormation.describeStacks()).willReturn(result);

        List<Stack> result = cloudformationClient.getStacks(tag1);
        assertThat(result.get(0).getTags().size(), is(1));
        assertThat(result.get(0).getTags().get(0).getKey(), is(tag1.getKey()));


    }
    @Test
    public void getStacksTag2Key() throws Exception {

        given(cloudFormation.describeStacks()).willReturn(result);

        List<Stack> result = cloudformationClient.getStacks(tag2);
        assertThat(result.get(0).getTags().size(), is(1));
        assertThat(result.get(0).getTags().get(0).getKey(), is(tag2.getKey()));


    }
    @Test
    public void getStacksTag2Value() throws Exception {

        given(cloudFormation.describeStacks()).willReturn(result);

        List<Stack> result = cloudformationClient.getStacks(tag2);
        assertThat(result.get(0).getTags().size(), is(1));
        assertThat(result.get(0).getTags().get(0).getValue(), is(tag2.getValue()));


    }
    @Test
    public void getStacksNothingResultsEmpty() throws Exception {

        given(cloudFormation.describeStacks()).willReturn(new DescribeStacksResult());

        List<Stack> result = cloudformationClient.getStacks(tag2);
        assertThat(result.size(), is(0));



    }
    @Test
    public void get2StacksTag2Value() throws Exception {
        //sorry, this is a pretty unreadable test
        result.withStacks(new Stack().withTags(tag2));
        given(cloudFormation.describeStacks()).willReturn(result);

        List<Stack> result = cloudformationClient.getStacks(tag2);

        assertThat(result.size(), is(2));
        assertThat(result.get(0).getTags().get(0).getValue(), is(tag2.getValue()));
        assertThat(result.get(1).getTags().get(0).getValue(), is(tag2.getValue()));
    }
    @Test
    public void get2StacksTag2Key() throws Exception {
        //sorry, this is a pretty unreadable test
        result.withStacks(new Stack().withTags(tag2));
        given(cloudFormation.describeStacks()).willReturn(result);

        List<Stack> result = cloudformationClient.getStacks(tag2);

        assertThat(result.size(), is(2));
        assertThat(result.get(0).getTags().get(0).getKey(), is(tag2.getKey()));
        assertThat(result.get(1).getTags().get(0).getKey(), is(tag2.getKey()));
    }
}