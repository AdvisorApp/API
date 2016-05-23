package com.advisorapp.api.test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by damien on 23/05/2016.
 */
public class TestHelper {

    public byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }
}
