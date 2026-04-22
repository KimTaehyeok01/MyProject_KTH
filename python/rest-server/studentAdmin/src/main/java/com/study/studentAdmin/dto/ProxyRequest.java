package com.study.studentAdmin.dto;

import java.util.Map;

public record ProxyRequest(
        String method,
        String path,
        Map<String, String> headers,
        String body
) {
}
