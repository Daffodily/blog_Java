package org.example.blog.dto;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Response {

    public static Map<String, Object> success(Map<String, Object> result) {
        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("result", result);
        return res;
    }

    public static Map<String, Object> fail(int code) {
        Map<String, Object> res = new HashMap<>();
        res.put("code", code);
        return res;
    }
}
