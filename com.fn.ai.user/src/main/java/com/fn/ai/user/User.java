package com.fn.ai.user;

import com.fn.ai.user.presentation.dto.UserSignInRequestDto;
import com.fn.ai.user.presentation.dto.UserSignUpRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

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
