package com.mynt.banking.auth;

import com.mynt.banking.user.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KycRepository extends JpaRepository<KycEntity,Long> {

    @NotNull
    @Override
    List<KycEntity> findAll();

    @NotNull
    List<KycEntity> findByApplicationId(String applicationId);

    List<KycEntity> findByWorkFlowRunId(String workFlowRunId);

    List<KycEntity> findByStatus(String status);

    List<KycEntity> findByUser(User user);
}
