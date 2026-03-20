package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.constants.LoanConstants;
import org.example.backend.dto.DecisionResponse;
import org.example.backend.exception.InvalidLoanAmount;
import org.example.backend.exception.InvalidLoanPeriod;
import org.example.backend.exception.NoLoanException;
import org.springframework.stereotype.Service;

/**
 * A service class that calculates maximum loan that we would approve of.
 * If no loan is approved based on current information then increases the loan period.
 */
@Service
@RequiredArgsConstructor
public class LoanDecisionCalculator {

    public DecisionResponse calculateMaximumApprovedAmount(int creditModifier, long loanAmount, int loanPeriod) {
        if (loanAmount < LoanConstants.MINIMUM_LOAN_AMOUNT || loanAmount > LoanConstants.MAXIMUM_LOAN_AMOUNT) {
            throw new InvalidLoanAmount("loanAmount must be between 2000€ and 10000€!");
        }
        if (loanPeriod < LoanConstants.MINIMUM_LOAN_PERIOD || loanPeriod > LoanConstants.MAXIMUM_LOAN_PERIOD) {
            throw new InvalidLoanPeriod("loanPeriod must be between 12 and 60 months!");
        }


        double initialScore = getCreditScore(creditModifier, loanAmount, loanPeriod);
        if (initialScore < LoanConstants.MINIMUM_CREDIT_SCORE) {
            return findApprovedLoanWithExtendedPeriod(creditModifier, loanAmount, loanPeriod);
        }

        int maximumLoan = calculateMaximumLoan(creditModifier, loanPeriod);
        return buildDecisionResponse(loanPeriod, maximumLoan);
    }

    private DecisionResponse findApprovedLoanWithExtendedPeriod(int creditModifier, long loanAmount, int loanPeriod) {
        for (int period = loanPeriod + 1; period <= LoanConstants.MAXIMUM_LOAN_PERIOD; period++) {
            double score = getCreditScore(creditModifier, loanAmount, period);
            if (score >= LoanConstants.MINIMUM_CREDIT_SCORE) {
                return buildDecisionResponse(period, calculateMaximumLoan(creditModifier, period));
            }
        }
        throw new NoLoanException("No valid loan found!");
    }

    private int calculateMaximumLoan(int creditModifier, int period) {
        return Math.min(LoanConstants.MAXIMUM_LOAN_AMOUNT, creditModifier * period);
    }

    private double getCreditScore(int creditModifier, long loanAmount, int loanPeriod) {
        return ((double) creditModifier / loanAmount) * loanPeriod;
    }

    private DecisionResponse buildDecisionResponse(int loanPeriod, int loanAmount) {
        DecisionResponse decisionResponse = new DecisionResponse();
        decisionResponse.setLoanPeriod(loanPeriod);
        decisionResponse.setLoanAmount(loanAmount);
        return decisionResponse;
    }
}
