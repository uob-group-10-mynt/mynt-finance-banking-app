package com.mynt.banking.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "CurrencyCloud")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyCloud {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "UUID", nullable = false, length = 100)
    private String UUID;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
