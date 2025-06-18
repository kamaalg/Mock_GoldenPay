package com.example.mock_golden_pay.dto;

import lombok.Data;

import java.time.Duration;

@Data
public class UserInfo {
    private String fin;
    private Integer salary;
    private Integer requested_amount;
    private Integer age;
    private Duration credit_duration;
}
