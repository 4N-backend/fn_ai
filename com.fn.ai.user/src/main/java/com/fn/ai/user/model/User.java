package com.fn.ai.user.model;

import com.fn.ai.common.context.UserRoleEnum;
import com.fn.ai.user.presentation.dto.UserSignUpRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "p_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String username;

  private String password;

  @Enumerated(EnumType.STRING)
  private UserRoleEnum role;

  public static User of(UserSignUpRequestDto requestDto) {
    return User.builder()
        .username(requestDto.username())
        .password(requestDto.password())
        .role(requestDto.role())
        .build();
  }
}
