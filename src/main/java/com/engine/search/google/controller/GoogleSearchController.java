package com.engine.search.google.controller;

import com.engine.search.google.json.Result;
import com.engine.search.google.json.SearchRequest;
import com.engine.search.google.service.GoogleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/google")
public class GoogleSearchController {

    private final GoogleService googleService;

    public GoogleSearchController(GoogleService googleService) {
        this.googleService = googleService;
    }

    @PostMapping(value = "/search")
    public ResponseEntity<?> search(@RequestBody SearchRequest request) throws IOException {
        List<Result> output = googleService.search(request.getKeywords());
        ObjectMapper mapper = new ObjectMapper();
//        return new ResponseEntity<>(url.get("template"), HttpStatus.OK);
        return new ResponseEntity<>(mapper.writeValueAsString(output), HttpStatus.OK);
    }
}
