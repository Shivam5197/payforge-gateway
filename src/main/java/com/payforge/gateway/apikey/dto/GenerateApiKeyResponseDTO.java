package com.payforge.gateway.apikey.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateApiKeyResponseDTO {

    private String apiKey;
}