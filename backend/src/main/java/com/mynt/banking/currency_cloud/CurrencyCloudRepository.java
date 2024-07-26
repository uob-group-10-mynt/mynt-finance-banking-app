package com.mynt.banking.currency_cloud;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrencyCloudRepository extends JpaRepository<CurrencyCloudEntity, String> {

    CurrencyCloudEntity findByUuid(String uuid);

    CurrencyCloudEntity findByUsersId(Long usersId);

}
