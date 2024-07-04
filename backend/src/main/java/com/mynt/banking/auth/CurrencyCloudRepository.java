package com.mynt.banking.auth;

import com.mynt.banking.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyCloudRepository extends JpaRepository<CurrencyCloud, Long> {

    // Assuming you want to find by uuid
    CurrencyCloud findByUuid(String uuid);

    // Assuming you want to find by user
    List<CurrencyCloud> findByUsers(User users);
}
