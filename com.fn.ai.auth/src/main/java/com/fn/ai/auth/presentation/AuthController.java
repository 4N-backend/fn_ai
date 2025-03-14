package com.fn.ai.auth.presentation;

import com.fn.ai.auth.application.AuthService;
import com.fn.ai.auth.presentation.dto.request.SignUpRequestDto;
import com.fn.ai.auth.presentation.dto.response.SignUpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signup(@RequestBody SignUpRequestDto requestDto) {
        return ResponseEntity.ok().body(authService.signup(requestDto));
    }
}
