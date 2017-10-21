package com.moodle.tenant.lambda;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by andrewlarsen on 9/10/17.
 */
public class ProxyRequestTest {
    private ProxyRequest proxyRequest;

    @Before
    public void setup(){
        proxyRequest=new ProxyRequest();
    }
    @Test
    public void getQueryParam() throws Exception {
        proxyRequest.setQueryStringParameters(new HashMap<String, String>(){{
            put("KEY", "VALUE");
        }});

        Optional<String> value = proxyRequest.getQueryParam("KEY");

        assertThat(value.get(), is("VALUE"));
    }
    @Test
    public void getQueryParamEmpty() throws Exception {
        proxyRequest.setQueryStringParameters(new HashMap<String, String>());

        Optional<String> value = proxyRequest.getQueryParam("KEY");

        assertThat(value.isPresent(), is(false));
    }
    @Test
    public void getPathParam() throws Exception {
        proxyRequest.setPathParameters(new HashMap<String, String>(){{
            put("KEY", "VALUE");
        }});

        Optional<String> value = proxyRequest.getPathParam("KEY");

        assertThat(value.get(), is("VALUE"));
    }
    @Test
    public void getPathParamEmpty() throws Exception {
        proxyRequest.setPathParameters(new HashMap<String, String>());

        Optional<String> value = proxyRequest.getPathParam("KEY");

        assertThat(value.isPresent(), is(false));
    }
}