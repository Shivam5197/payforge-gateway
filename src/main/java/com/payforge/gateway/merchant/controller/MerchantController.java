package com.payforge.gateway.merchant.controller;

import com.payforge.gateway.merchant.dto.CreateMerchantRequestDTO;
import com.payforge.gateway.merchant.dto.MerchantResponseDTO;
import com.payforge.gateway.merchant.entity.Merchant;
import com.payforge.gateway.merchant.repository.MerchantRepository;
import com.payforge.gateway.merchant.service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MerchantResponseDTO createMerchant(
            @Valid
            @RequestBody
            CreateMerchantRequestDTO request) {

        return merchantService.createMerchant(request);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<Merchant> getAllMerchants() {
        return merchantService.findAllMerchants();
    }

    @GetMapping("/{merchantId}")
    public MerchantResponseDTO getMerchant(
            @PathVariable UUID merchantId) {

        return merchantService.getMerchantById(
                merchantId);
    }

    @GetMapping("email/{email}")
    public MerchantResponseDTO getMerchant(
            @PathVariable String email) {

        return merchantService.getMerchantByEmail(
                email);
    }
}