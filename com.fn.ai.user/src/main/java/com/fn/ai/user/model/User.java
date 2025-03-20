package com.fn.ai.user.model;

import com.fn.ai.common.context.UserRoleEnum;
import com.fn.ai.common.entity.BaseEntity;
import com.fn.ai.user.model.vo.Password;
import com.fn.ai.user.model.vo.SlackId;
import com.fn.ai.user.model.vo.Username;
import com.fn.ai.user.presentation.dto.UserSignUpRequestDto;
import jakarta.persistence.*;

import java.util.UUID;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
@Table(name = "p_user")
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "user_id")
  private UUID id;

  @Embedded
  private Username username;

  @Embedded
  private Password password;

  @Enumerated(EnumType.STRING)
  private UserRoleEnum role;

  @Embedded
  private SlackId slackId;


  public static User of(UserSignUpRequestDto requestDto) {
    return User.builder()
            .username(new Username(requestDto.username()))
            .password(new Password(requestDto.password()))
            .role(requestDto.role())
            .slackId(new SlackId(requestDto.slackId()))
            .build();
  }
}
