package com.ssafy.finalproject.aptsale.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AptTransactionResDTO {
    private LocalDate transactionDate;
    private Integer transactionAmount;
}