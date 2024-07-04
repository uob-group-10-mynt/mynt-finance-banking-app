package com.mynt.banking.auth;

import com.mynt.banking.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"Currency_Cloud\"")
public class CurrencyCloud {
    @Id
    @Column(name = "\"UUID\"", nullable = false, length = Integer.MAX_VALUE)
    private String uuid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "users_id", nullable = false)
    private User users;

}