package com.payforge.gateway.merchant.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MerchantResponseDTO {

    private UUID uuid;
    private String merchantName;
    private String email;
    private String status;

}
