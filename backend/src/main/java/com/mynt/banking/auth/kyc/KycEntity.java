package com.mynt.banking.auth.kyc;

import com.mynt.banking.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@Entity
@Table(name = "kyc")
@NoArgsConstructor
@AllArgsConstructor
public class KycEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "application_id", nullable = false, length = Integer.MAX_VALUE)
    private String applicationId;

    @NotNull
    @Column(name = "work_flow_run_id", nullable = false, length = Integer.MAX_VALUE)
    private String workFlowRunId;

    @Column(name = "status", length = Integer.MAX_VALUE)
    private String status;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}