package com.mynt.banking.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KycRepository extends JpaRepository<Kyc, Long> {

    Kyc findApplicantByApplicantId(String applicantId);
}
