package com.mynt.banking.repository;

import com.mynt.banking.entity.CurrencyCloud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CurrencyCloudRepository extends JpaRepository<CurrencyCloud, Long> {
}
