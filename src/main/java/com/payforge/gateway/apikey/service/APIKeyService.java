package com.payforge.gateway.apikey.service;

import com.payforge.gateway.apikey.dto.GenerateApiKeyResponseDTO;

import java.util.UUID;

public interface APIKeyService {
    GenerateApiKeyResponseDTO generateApiKey(
            UUID merchantId);
}
