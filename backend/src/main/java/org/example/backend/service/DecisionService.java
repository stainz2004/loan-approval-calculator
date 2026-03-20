package org.example.backend.service;

import org.example.backend.dto.DecisionRequest;
import org.example.backend.dto.DecisionResponse;
import org.springframework.stereotype.Service;

@Service
public class DecisionService {

    private static final Integer SEGMENT_1_CREDIT_MODIFIER = 100;
    private static final Integer SEGMENT_2_CREDIT_MODIFIER = 300;
    private static final Integer SEGMENT_3_CREDIT_MODIFIER = 1000;
    private static final Integer SEGMENT_DEBT_CREDIT_MODIFIER = 0;
    private static final Double minimumCreditScore = 1.00;
    private static final Integer MAXIMUM_LOAN_PERIOD = 60;


    public DecisionResponse calculateMaximumApprovedLoan(DecisionRequest decisionRequest) {
        String personalCode = decisionRequest.getPersonalCode();
        Long loanAmount = decisionRequest.getLoanAmount();
        int loamPeriod = decisionRequest.getLoanPeriod();

        int creditModifier = getCreditModifier(personalCode);
        if (creditModifier == 0) {
            //TODO throw an error
        }

        while (getCreditScore(creditModifier, loanAmount, loamPeriod) < minimumCreditScore) {
            loanAmount++;
            if (loanAmount > MAXIMUM_LOAN_PERIOD) {
                //TODO throw an error
            }
        }

        int maxLoan;

        return null;
    }


    private int getCreditModifier(String personalCode) {
        int lastNumberOfPersonalCode = Integer.getInteger(String.valueOf(personalCode.charAt(personalCode.length() - 1)));
        if (lastNumberOfPersonalCode < 2) {
            return SEGMENT_DEBT_CREDIT_MODIFIER;
        } else if (lastNumberOfPersonalCode < 4) {
            return SEGMENT_1_CREDIT_MODIFIER;
        } else if (lastNumberOfPersonalCode < 7) {
            return SEGMENT_2_CREDIT_MODIFIER;
        } else {
            return SEGMENT_3_CREDIT_MODIFIER;
        }
    }

    private long getCreditScore(int creditModifier, Long loanAmount, int loanPeriod) {
        return (creditModifier / loanAmount) * loanPeriod;
    }

    private int getHighestLoanAmount() {
        return 0;
    }
}
