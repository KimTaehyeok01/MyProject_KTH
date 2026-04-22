package com.study.studentAdmin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "python.server")
public record PythonServerProperties(String baseUrl) {
}
