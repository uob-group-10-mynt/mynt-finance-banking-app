package com.mynt.banking.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CurrencyCloud")
public class CurrencyCloud {
    @Id
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "uuid", nullable = false, length = 100)
    private String uuid;

}