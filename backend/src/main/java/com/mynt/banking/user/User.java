package com.mynt.banking.user;

import com.mynt.banking.auth.CurrencyCloud;
import com.mynt.banking.auth.Kyc;
import com.mynt.banking.token.Token;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_users", schema = "public")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname", nullable = false, length = 50)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 50)
    private String lastname;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @NotNull
    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Size(max = 100)
    @NotNull
    @Column(name = "\"Address\"", nullable = false, length = 100)
    private String address;

    @Size(max = 100)
    @NotNull
    @Column(name = "\"Phone_Number\"", nullable = false, length = 100)
    private String phoneNumber;

    @OneToMany(mappedBy = "users")
    @Builder.Default
    private Set<CurrencyCloud> currencyClouds = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private Set<Kyc> kycs = new LinkedHashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() { return email; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
