package com.mynt.banking.auth;

import com.mynt.banking.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KycRepository extends JpaRepository<KycEntity,Long> {



}
