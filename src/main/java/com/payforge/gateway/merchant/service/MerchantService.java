package com.payforge.gateway.merchant.service;

import com.payforge.gateway.merchant.dto.CreateMerchantRequestDTO;
import com.payforge.gateway.merchant.dto.MerchantResponseDTO;
import com.payforge.gateway.merchant.entity.Merchant;

import java.util.List;
import java.util.UUID;

public interface MerchantService {

    MerchantResponseDTO createMerchant(CreateMerchantRequestDTO requestDTO);

    List<Merchant> findAllMerchants();

    MerchantResponseDTO getMerchantByEmail(String email);
    MerchantResponseDTO getMerchantById(UUID uuid);

}
