package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.DecisionRequest;
import org.example.backend.dto.DecisionResponse;
import org.example.backend.exception.NoLoanException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DecisionService {

    private final LoanDecisionCalculator loanDecisionCalculator;
    private final CreditModifierCalculator creditModifierCalculator;


    public DecisionResponse calculateMaximumApprovedLoan(DecisionRequest decisionRequest) {
        String personalCode = decisionRequest.getPersonalCode();
        Long loanAmount = decisionRequest.getLoanAmount();
        int loanPeriod = decisionRequest.getLoanPeriod();

        int creditModifier = creditModifierCalculator.getCreditModifier(personalCode);
        if (creditModifier == 0) {
            throw new NoLoanException("No valid loan found!");
        }

        return loanDecisionCalculator.calculateMaximumApprovedAmount(creditModifier, loanAmount, loanPeriod);
    }

}
