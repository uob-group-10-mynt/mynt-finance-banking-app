package com.mynt.mynt.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.ColumnDefault

@Entity
@Table(name = "\"User\"")
open class User {
    @Id
    @ColumnDefault("nextval('" User_id_seq "'::regclass)")
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @Column(name = "forename", nullable = false, length = 100)
    open var forename: String? = null

    @Column(name = "surname", nullable = false, length = 100)
    open var surname: String? = null

    @Column(name = "email", nullable = false, length = 100)
    open var email: String? = null

    @Column(name = "password", nullable = false, length = 200)
    open var password: String? = null
}