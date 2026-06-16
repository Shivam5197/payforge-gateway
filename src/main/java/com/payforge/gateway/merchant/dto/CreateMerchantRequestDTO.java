package com.payforge.gateway.merchant.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateMerchantRequestDTO {

    @NotBlank
    private String merchantName;

    @Email
    @NotBlank
    private String email;
}
