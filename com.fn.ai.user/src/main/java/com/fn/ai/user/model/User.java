package com.fn.ai.user.model;

import com.fn.ai.user.model.enums.UserRoleEnum;
import com.fn.ai.user.model.vo.Username;
import com.fn.ai.user.model.vo.Password;
import com.fn.ai.user.model.vo.SlackId;
import com.fn.ai.user.presentation.dto.UserSignUpRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
@Table(name = "p_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Embedded
    private Username username;

    @Embedded
    private Password password;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @Embedded
    private SlackId slackId;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DeliveryManager deliveryManager;

    public static User of(UserSignUpRequestDto requestDto) {
        return User.builder()
                .username(new Username(requestDto.username()))
                .password(new Password(requestDto.password()))
                .role(requestDto.role())
                .slackId(new SlackId(requestDto.slackId()))
                .build();
    }
}
