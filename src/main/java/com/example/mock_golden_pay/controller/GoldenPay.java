package com.example.mock_golden_pay.controller;

import com.example.mock_golden_pay.dto.CreditEvaluationResult;
import com.example.mock_golden_pay.dto.CreditTier;
import com.example.mock_golden_pay.dto.FinRepoObject;
import com.example.mock_golden_pay.dto.UserInfo;
import com.example.mock_golden_pay.repository.FinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
public class GoldenPay {
    private final FinRepository finrepository;
    @PostMapping("/evaluate_client")
    public ResponseEntity<?> evaluate_user(@RequestBody UserInfo user_info){
        Duration minDuration = Duration.ofDays(6L * 30);     // ≈ 6 months
        Duration maxDuration = Duration.ofDays(20L * 365);   // ≈ 20 years
        Integer age = user_info.getAge();
        if(age<18 || age >65){
            return ResponseEntity.status(HttpStatus.OK).body(new CreditEvaluationResult(false,"Credit is not given for the provided age."));

        }else if (user_info.getCredit_duration().compareTo(minDuration) < 0 || user_info.getCredit_duration().compareTo(maxDuration) > 0) {
            String err = String.format(
                    "Credit duration must be between %d months and %d years.",
                    6, 20
            );
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new CreditEvaluationResult(false, err));
        }
        else if (user_info.getSalary() == null || user_info.getSalary() < 345) {
            return ResponseEntity.status(HttpStatus.OK).body(new CreditEvaluationResult(false,"Salary is not provided or lower than the minimal pay."));
        }
        else{
            Integer max_allowed_amount = user_info.getSalary()*4;
            if(user_info.getRequested_amount()>max_allowed_amount){
                String msg = String.format(
                        "Credit amount is too large for the given salary. Max amount that can be given is %d.",
                        max_allowed_amount
                );
                return ResponseEntity.status(HttpStatus.OK).body(new CreditEvaluationResult(false,msg));
            }
            float interest_rate = CreditTier.percentageFor(user_info.getSalary());
            if(interest_rate == 0){
                return ResponseEntity.status(HttpStatus.OK).body(new CreditEvaluationResult(false,"Either salary is too low(<345) or something went wrong in the server"));

            }
            FinRepoObject finRepoObject = new FinRepoObject();
            finRepoObject.setFin(user_info.getFin());
            finRepoObject.setSalary(user_info.getSalary());
            finRepoObject.setAmountGiven(user_info.getRequested_amount());
            finRepoObject.setInterestRate(interest_rate);
            finrepository.save(finRepoObject);
            String msg = String.format(
                    "Congratulations!!!.Credit was successfully provided to you from GIVEN bank.Your annual interest rate will be %.2f%%  and the given amount will be %d",
                    interest_rate,user_info.getRequested_amount()
            );
            return ResponseEntity.status(HttpStatus.OK).body(new CreditEvaluationResult(true,msg));



        }
    }
}
