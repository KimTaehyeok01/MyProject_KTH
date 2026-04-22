package com.study.studentAdmin.dto;

import java.util.Map;

public record ProxyResult(
        int status,
        String reason,
        String body,
        Map<String, String> headers,
        String targetUrl
) {
}
