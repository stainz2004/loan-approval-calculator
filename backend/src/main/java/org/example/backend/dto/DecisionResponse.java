package org.example.backend.dto;

import lombok.Data;

@Data
public class DecisionResponse {
    private int loanAmount;
    private int loanPeriod;
}
