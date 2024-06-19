package com.mynt.banking.service;

import com.mynt.banking.entity.CurrencyCloud;
import com.mynt.banking.repository.CurrencyCloudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyCloudService {
    private final CurrencyCloudRepository currencyCloudRepository;

    public CurrencyCloud createCurrencyCloud(CurrencyCloud currencyCloud) {
        return currencyCloudRepository.save(currencyCloud);
    }

    public CurrencyCloud getCurrencyCloudByUserId(Long userId) {
        return currencyCloudRepository.findById(userId).orElse(null);
    }

    public List<CurrencyCloud> getAllCurrencyClouds() {
        return currencyCloudRepository.findAll();
    }

    public void deleteCurrencyCloud(Long userId) {
        currencyCloudRepository.deleteById(userId);
    }
}
