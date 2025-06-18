package com.example.mock_golden_pay.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class FinRepoObject {
    @Id
    private String fin;
    private Integer salary;
    private Integer amountGiven;
    private float interestRate;
}
