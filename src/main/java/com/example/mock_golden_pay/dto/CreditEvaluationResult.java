package com.example.mock_golden_pay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreditEvaluationResult {
    public boolean result;
    public String reason;
}
