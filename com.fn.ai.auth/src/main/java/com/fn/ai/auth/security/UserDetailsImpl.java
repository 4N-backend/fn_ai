package com.fn.ai.auth.security;

import com.fn.ai.auth.application.client.dto.UserResponseDto;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class UserDetailsImpl implements UserDetails {

  private final UUID id;

  private final String username;

  private final String password;

  private final UserRoleEnum role;

  private final Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(UserResponseDto user) {
    this.id = user.id();
    this.username = user.username();
    this.password = user.password();
    this.role = user.role();
    this.authorities = generateAuthorities(user.role());
  }

  private Collection<GrantedAuthority> generateAuthorities(UserRoleEnum role) {
    return List.of(new SimpleGrantedAuthority(role.getAuthority()));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public UUID getUserId() {
    return this.id;
  }

  public UserRoleEnum getRole() {
    return this.role;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
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
