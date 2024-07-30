package com.mynt.banking.auth;

import com.mynt.banking.currency_cloud.CurrencyCloudEntity;
import com.mynt.banking.currency_cloud.CurrencyCloudRepository;
import com.mynt.banking.user.User;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class JwtUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final String uuid;
    private final List<GrantedAuthority> authorities;

    public JwtUserDetails(@NotNull User user, CurrencyCloudRepository currencyCloudRepository) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.authorities = Stream.of(user.getRole())
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());

        // Fetch UUID from CurrencyCloudRepository
        this.uuid = getUuidFromCurrencyCloud(user.getId(), currencyCloudRepository);
    }

    private String getUuidFromCurrencyCloud(Long userId, @NotNull CurrencyCloudRepository currencyCloudRepository) {
        return currencyCloudRepository.findByUsersId(userId)
                .map(CurrencyCloudEntity::getUuid)
                .orElse(null); // Handle case where UUID is not found
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

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
