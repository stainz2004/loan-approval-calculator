package org.example.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.backend.dto.DecisionRequest;
import org.example.backend.dto.DecisionResponse;
import org.example.backend.service.DecisionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loan")
@RequiredArgsConstructor
public class DecisionController {

    private final DecisionService decisionService;


    @PostMapping("/decision")
    public ResponseEntity<DecisionResponse> requestDecision(@Valid @RequestBody DecisionRequest decisionRequest) {
        DecisionResponse decisionResponse = decisionService.calculateMaximumApprovedLoan(decisionRequest);
    }
}
