package com.example.mock_golden_pay.repository;

import com.example.mock_golden_pay.dto.FinRepoObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface    FinRepository extends JpaRepository<FinRepoObject, String> {

}
