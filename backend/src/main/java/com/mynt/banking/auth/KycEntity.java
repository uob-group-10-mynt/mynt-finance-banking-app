package com.mynt.banking.auth;

import com.mynt.banking.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "kyc")
public class KycEntity {
    @Id
    @Column(name = "key", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "\"Application_ID\"", nullable = false, length = Integer.MAX_VALUE)
    private String applicationId;

    @NotNull
    @Column(name = "\"Work_Flow_Run_ID\"", nullable = false, length = Integer.MAX_VALUE)
    private String workFlowRunId;

    @Column(name = "\"Status\"", length = Integer.MAX_VALUE)
    private String status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}