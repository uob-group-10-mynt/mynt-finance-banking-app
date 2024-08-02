package com.mynt.banking.currency_cloud.repo;

import com.mynt.banking.user.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyCloudRepository extends JpaRepository<CurrencyCloudEntity, String> {

    Optional<CurrencyCloudEntity> findByUuid(String uuid);

    Optional<CurrencyCloudEntity> findByUser(@NotNull User user);
}
