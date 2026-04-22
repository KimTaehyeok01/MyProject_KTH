package com.study.studentAdmin.controller;

import com.study.studentAdmin.dto.ProxyRequest;
import com.study.studentAdmin.dto.ProxyResult;
import com.study.studentAdmin.service.PythonProxyService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentCrudProxyController {

    private static final Map<String, String> JSON_HEADER = Map.of("Content-Type", "application/json");

    private final PythonProxyService pythonProxyService;

    public StudentCrudProxyController(PythonProxyService pythonProxyService) {
        this.pythonProxyService = pythonProxyService;
    }

    @GetMapping
    public ResponseEntity<String> listStudents(@RequestParam(required = false) String q) {
        String path = "/students";
        if (q != null && !q.isBlank()) {
            String encodedQ = UriUtils.encodeQueryParam(q, StandardCharsets.UTF_8);
            path = path + "?q=" + encodedQ;
        }
        return toResponse(pythonProxyService.proxy(new ProxyRequest("GET", path, Map.of(), null)));
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<String> getStudent(@PathVariable String studentId) {
        return toResponse(pythonProxyService.proxy(new ProxyRequest("GET", "/students/" + studentId, Map.of(), null)));
    }

    @PostMapping
    public ResponseEntity<String> createStudent(@RequestBody String body) {
        return toResponse(pythonProxyService.proxy(new ProxyRequest("POST", "/students", JSON_HEADER, body)));
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<String> updateStudent(@PathVariable String studentId, @RequestBody String body) {
        return toResponse(pythonProxyService.proxy(new ProxyRequest("PUT", "/students/" + studentId, JSON_HEADER, body)));
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable String studentId) {
        return toResponse(pythonProxyService.proxy(new ProxyRequest("DELETE", "/students/" + studentId, Map.of(), null)));
    }

    private ResponseEntity<String> toResponse(ProxyResult result) {
        HttpHeaders headers = new HttpHeaders();
        result.headers().forEach(headers::add);
        return new ResponseEntity<>(result.body(), headers, HttpStatusCode.valueOf(result.status()));
    }
}
