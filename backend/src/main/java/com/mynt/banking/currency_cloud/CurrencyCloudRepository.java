package com.mynt.banking.currency_cloud;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CurrencyCloudRepository extends JpaRepository<CurrencyCloudEntity, String> {

    Optional<CurrencyCloudEntity> findByUuid(String uuid);

    Optional<CurrencyCloudEntity> findByUsersId(Long usersId);
}
