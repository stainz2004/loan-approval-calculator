package org.example.backend.service;

import org.example.backend.constants.LoanConstants;
import org.example.backend.exception.PersonalCodeException;
import org.springframework.stereotype.Service;

@Service
public class CreditModifierCalculator {


    public int getCreditModifier(String personalCode) {
        if (personalCode == null || personalCode.isBlank()) {
            throw new PersonalCodeException("Personal code cannot be empty");
        }

        if (personalCode.length() != 11) {
            throw new PersonalCodeException("Personal code must be 11 characters long!");
        }

        int lastDigit = extractLastDigit(personalCode);

        if (lastDigit < 2) return LoanConstants.SEGMENT_DEBT_CREDIT_MODIFIER;
        if (lastDigit < 4) return LoanConstants.SEGMENT_1_CREDIT_MODIFIER;
        if (lastDigit < 7) return LoanConstants.SEGMENT_2_CREDIT_MODIFIER;
        return LoanConstants.SEGMENT_3_CREDIT_MODIFIER;
    }

    private static int extractLastDigit(String personalCode) {
        char lastChar = personalCode.charAt(personalCode.length() - 1);
        if (!Character.isDigit(lastChar)) {
            throw new PersonalCodeException("Personal code must not contain characters!");
        }
        return Character.getNumericValue(lastChar);
    }
}
