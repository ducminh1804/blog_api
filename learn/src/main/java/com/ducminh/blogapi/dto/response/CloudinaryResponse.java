package com.ducminh.blogapi.dto.response;

import java.util.Map;

public class CloudinaryResponse {
    public static Map toCloudinayResponse(Map cloud) {
        return Map.of(
                "url", cloud.get("url"),
                "version", cloud.get("version"),
                "displayName", cloud.get("display_name"),
                "format", cloud.get("format")
        );
    }
}
