package com.payforge.gateway.merchant.service;

import com.payforge.gateway.merchant.dto.CreateMerchantRequestDTO;
import com.payforge.gateway.merchant.dto.MerchantResponseDTO;
import com.payforge.gateway.merchant.entity.Merchant;

import java.util.List;

public interface MerchantService {

    MerchantResponseDTO createMerchant(CreateMerchantRequestDTO requestDTO);

    List<Merchant> findAllMerchants();
}
