package com.mynt.banking.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);

  @Query("SELECT k.status FROM KycEntity k JOIN k.user u WHERE u.email = :email")
  Optional<String> getKycStatus(@Param("email") String email);
}