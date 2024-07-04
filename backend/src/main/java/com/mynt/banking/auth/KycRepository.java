package com.mynt.banking.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KycRepository extends JpaRepository<Kyc, Long> {

    Kyc findApplicantByApplicantId(String applicantId);
    Kyc findWorkerByWorkerId(String workFlowId);
    Kyc findByStatusAndApplicantId(String status, String applicantId);
    Kyc findByUserId(String user);
}
