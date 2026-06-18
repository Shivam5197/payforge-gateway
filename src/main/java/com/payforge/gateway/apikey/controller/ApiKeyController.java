package com.payforge.gateway.apikey.controller;

import com.payforge.gateway.apikey.dto.GenerateApiKeyResponseDTO;
import com.payforge.gateway.apikey.service.APIKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/merchants")
@RequiredArgsConstructor
public class ApiKeyController {

    private final APIKeyService apiService;

    @PostMapping("/{merchantId}/api-keys")
    @ResponseStatus(HttpStatus.CREATED)
    public GenerateApiKeyResponseDTO
    generateApiKey(
            @PathVariable UUID merchantId) {

        return apiService
                .generateApiKey(merchantId);
    }


    @RestController
    @RequestMapping("/api/v1/test")
    public class TestController {

        @GetMapping
        public String test() {

            return "Authenticated";
        }
    }
}
