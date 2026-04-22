package com.study.studentAdmin.controller;

import com.study.studentAdmin.dto.ProxyRequest;
import com.study.studentAdmin.dto.ProxyResult;
import com.study.studentAdmin.service.PythonProxyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/python")
public class PythonProxyController {

    private final PythonProxyService pythonProxyService;

    public PythonProxyController(PythonProxyService pythonProxyService) {
        this.pythonProxyService = pythonProxyService;
    }

    @GetMapping("/health")
    public ProxyResult healthCheck() {
        return pythonProxyService.proxy(new ProxyRequest("GET", "/health", Map.of(), null));
    }

    @PostMapping("/proxy")
    public ProxyResult proxy(@RequestBody ProxyRequest request) {
        return pythonProxyService.proxy(request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "HTTP method must be one of GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS"));
    }
}
