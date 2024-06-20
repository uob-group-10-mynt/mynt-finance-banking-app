package com.mynt.mynt.model

import jakarta.persistence.*

@Entity
@Table(name = "\"CurrencyCloud\"")
open class CurrencyCloud {
    @Id
    @Column(name = "user_id", nullable = false)
    open var id: Int? = null

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    open var user: User? = null

    @Column(name = "\"UUID\"", nullable = false, length = 100)
    open var uuid: String? = null
}