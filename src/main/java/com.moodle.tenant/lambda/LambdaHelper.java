package com.moodle.tenant.lambda;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrewlarsen on 10/20/17.
 */
public class LambdaHelper {

    /**
     * Helper method to create cors headers for Browsers
     * @return
     */
    public static Map<String, String> getCorsHeaders(){
        Map headers = new HashMap<String, String>();
        headers.put( "Access-Control-Allow-Origin", "*");
        return headers;
    }

}
