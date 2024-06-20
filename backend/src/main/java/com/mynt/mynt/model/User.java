package com.mynt.mynt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "\"User\"")
public class User {
    @Id
    @ColumnDefault("nextval('"User_id_seq"'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "forename", nullable = false, length = 100)
    private String forename;

    @Column(name = "surname", nullable = false, length = 100)
    private String surname;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 200)
    private String password;

}