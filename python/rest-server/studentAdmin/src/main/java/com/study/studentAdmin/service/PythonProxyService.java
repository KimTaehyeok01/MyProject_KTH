package com.study.studentAdmin.service;

import com.study.studentAdmin.config.PythonServerProperties;
import com.study.studentAdmin.dto.ProxyRequest;
import com.study.studentAdmin.dto.ProxyResult;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class PythonProxyService {

    private final RestTemplate pythonRestTemplate;
    private final PythonServerProperties properties;

    public PythonProxyService(RestTemplate pythonRestTemplate, PythonServerProperties properties) {
        this.pythonRestTemplate = pythonRestTemplate;
        this.properties = properties;
    }

    public ProxyResult proxy(ProxyRequest request) {
        String methodText = request.method() == null || request.method().isBlank()
                ? HttpMethod.GET.name()
                : request.method().trim().toUpperCase();

        HttpMethod method = HttpMethod.valueOf(methodText);
        String path = normalizePath(request.path());
        String targetUrl = buildTargetUrl(path);
        Map<String, String> headers = request.headers() == null ? Collections.emptyMap() : request.headers();
        String body = request.body();

        HttpHeaders outboundHeaders = new HttpHeaders();
        headers.forEach(outboundHeaders::set);
        if (body != null && !body.isBlank() && !hasContentType(headers)) {
            outboundHeaders.setContentType(MediaType.APPLICATION_JSON);
        }

        HttpEntity<String> entity = new HttpEntity<>(body, outboundHeaders);

        try {
            ResponseEntity<String> response = pythonRestTemplate.exchange(targetUrl, method, entity, String.class);

            return new ProxyResult(
                    response.getStatusCode().value(),
                    Objects.requireNonNullElse(response.getStatusCode().toString(), ""),
                    response.getBody(),
                    flattenHeaders(response.getHeaders()),
                    targetUrl
            );
        } catch (HttpStatusCodeException ex) {
            return new ProxyResult(
                    ex.getStatusCode().value(),
                    ex.getStatusText(),
                    ex.getResponseBodyAsString(),
                    flattenHeaders(ex.getResponseHeaders() != null ? ex.getResponseHeaders() : new HttpHeaders()),
                    targetUrl
            );
        }
    }

    private String normalizePath(String rawPath) {
        if (rawPath == null || rawPath.isBlank()) {
            return "/";
        }

        String trimmedPath = rawPath.trim();
        return trimmedPath.startsWith("/") ? trimmedPath : "/" + trimmedPath;
    }

    private String buildTargetUrl(String path) {
        return properties.baseUrl() + path;
    }

    private Map<String, String> flattenHeaders(HttpHeaders headers) {
        if (headers == null || headers.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, String> mappedHeaders = new LinkedHashMap<>();
        headers.forEach((key, value) -> mappedHeaders.put(key, String.join(",", value)));
        return mappedHeaders;
    }

    private boolean hasContentType(Map<String, String> headers) {
        return headers.keySet().stream().anyMatch(key -> "content-type".equalsIgnoreCase(key));
    }
}
