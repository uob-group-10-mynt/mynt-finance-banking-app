package com.mynt.banking.auth;

import com.mynt.banking.user.User;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KycRepository extends JpaRepository<KycEntity, Long> {

    @NotNull
    @Override
    List<KycEntity> findAll();

    KycEntity findByUser(User user);

//    @Modifying
//    @Transactional
//    @Query("SELECT * FROM _users WHERE _users.id = :id")
//    void selectKycUser(@Param("id") Long id);


    @Modifying
    @Transactional
    @Query("UPDATE KycEntity e SET e.status = :status WHERE e.id = :id")
    void updateStatus(@Param("status") String status, @Param("id") Long id);
}