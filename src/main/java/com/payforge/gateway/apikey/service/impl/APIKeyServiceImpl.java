package com.payforge.gateway.apikey.service.impl;

import com.payforge.gateway.apikey.dto.GenerateApiKeyResponseDTO;
import com.payforge.gateway.apikey.entity.ApiKey;
import com.payforge.gateway.apikey.repository.ApiKeyRepository;
import com.payforge.gateway.apikey.service.APIKeyService;
import com.payforge.gateway.common.security.ApiKeyGenerator;
import com.payforge.gateway.common.utility.HashUtil;
import com.payforge.gateway.exceptions.merchant.MerchantNotFoundException;
import com.payforge.gateway.merchant.entity.Merchant;
import com.payforge.gateway.merchant.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class APIKeyServiceImpl implements APIKeyService {
    /**
     * @param merchantId
     * @return
     */
    private final MerchantRepository merchantRepository;
    private final ApiKeyRepository apiKeyRepository;
    @Override
    @Transactional
    public GenerateApiKeyResponseDTO generateApiKey(UUID merchantId) {

        Merchant merchant =
                merchantRepository.findById(merchantId)
                        .orElseThrow(() ->
                                new MerchantNotFoundException(
                                        "Merchant not found : "
                                                + merchantId , HttpStatus.NOT_FOUND));

        String apiKey =
                ApiKeyGenerator.generate();

        String keyHash =
                HashUtil.sha256(apiKey);

        ApiKey entity =
                ApiKey.builder()
                        .id(UUID.randomUUID())
                        .merchant(merchant)
                        .keyHash(keyHash)
                        .active(true)
                        .createdAt(LocalDateTime.now())
                        .build();


        apiKeyRepository.save(entity);

        return GenerateApiKeyResponseDTO
                .builder()
                .apiKey(apiKey)
                .build();
    }
}
