package com.payforge.gateway.merchant.service.impl;

import com.payforge.gateway.merchant.dto.CreateMerchantRequestDTO;
import com.payforge.gateway.merchant.dto.MerchantResponseDTO;
import com.payforge.gateway.merchant.entity.Merchant;
import com.payforge.gateway.merchant.entity.MerchantStatus;
import com.payforge.gateway.merchant.repository.MerchantRepository;
import com.payforge.gateway.merchant.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;

    /**
     * @param requestDTO
     * @return
     */
    @Override
    public MerchantResponseDTO createMerchant(CreateMerchantRequestDTO requestDTO) {

        if (merchantRepository.existsByEmail(requestDTO.getEmail())) {
            throw new RuntimeException("Merchant email Id already Exists! ");
        }

        Merchant merchant = Merchant.builder().merchantName(requestDTO.getMerchantName())
                .email(requestDTO.getEmail())
                .status(MerchantStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        merchantRepository.save(merchant);
        return MerchantResponseDTO.builder().
                uuid(merchant.getId())
                .email(merchant.getEmail())
                .merchantName(merchant.getMerchantName())
                .status(merchant.getStatus().name())
                .build();
    }

    /**
     * @return
     */
    @Override
    public List<Merchant> findAllMerchants() {
        return merchantRepository.findAll();
    }
}
