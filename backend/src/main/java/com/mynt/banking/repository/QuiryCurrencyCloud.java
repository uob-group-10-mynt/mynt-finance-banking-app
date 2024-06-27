package com.mynt.banking.repository;
import com.mynt.banking.user.CurrencyCloud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuiryCurrencyCloud extends JpaRepository<CurrencyCloud, Long> {

    @Query(value = "SELECT * FROM \"CurrencyCloud\";", nativeQuery = true)
    List<CurrencyCloud> findAllData();

}
