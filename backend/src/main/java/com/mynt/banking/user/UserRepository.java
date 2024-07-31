package com.mynt.banking.user;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  @Query("SELECT k.status FROM KycEntity k JOIN k.user u WHERE u.email = :email")
  Optional<String> getKycStatus(@Param("email") String email);

//  @Modifying
//  @Transactional
//  @Query("SELECT * FROM _users WHERE _users.id = :id")
//  Optional<String> selectKycUser(@Param("id") Long id);
}
