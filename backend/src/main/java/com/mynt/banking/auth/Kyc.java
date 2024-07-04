package com.mynt.banking.auth;

import com.mynt.banking.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "kyc", schema = "public")
public class Kyc {

    @Id
    @GeneratedValue
    @Column(name = "\"Applicant_ID\"", nullable = false, length = Integer.MAX_VALUE)
    private String applicantId;

    @NotNull
    @Column(name = "\"Work_Flow_Run_ID\"", nullable = false, length = Integer.MAX_VALUE)
    private String workFlowRunId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "\"Status\"", nullable = false, length = Integer.MAX_VALUE)
    private String status;

}