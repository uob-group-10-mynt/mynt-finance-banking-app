package com.mynt.banking.auth;

import com.mynt.banking.user.Role;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public record MyntUserDetails(String username, String uuid, Role role) implements UserDetails {

    @NotNull
    @Override
    public List<SimpleGrantedAuthority> getAuthorities() { return role.getAuthorities(); }

    @Nullable
    @Override
    public String getPassword() { return null; }

    @Override
    public String getUsername() { return username; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
