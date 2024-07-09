package com.mynt.banking.currency_cloud;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"Currency_Cloud\"")
public class CurrencyCloudEntity {
    @Id
    @Column(name = "\"UUID\"", nullable = false, length = Integer.MAX_VALUE)
    private String uuid;

    @NotNull
    @Column(name = "users_id", nullable = false)
    private Long usersId;

}