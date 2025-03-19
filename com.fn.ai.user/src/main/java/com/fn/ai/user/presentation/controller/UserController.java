package com.fn.ai.user.presentation.controller;

import com.fn.ai.user.application.service.UserService;
import com.fn.ai.user.presentation.dto.UserSignInResponseDto;
import com.fn.ai.user.presentation.dto.UserSignUpRequestDto;
import com.fn.ai.user.presentation.dto.UserSignUpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<UserSignUpResponseDto> signup(
      @RequestBody UserSignUpRequestDto requestDto) {
    UserSignUpResponseDto responseDto = userService.signup(requestDto);
    return ResponseEntity.ok().body(responseDto);
  }

  @GetMapping("/{username}")
  public ResponseEntity<UserSignInResponseDto> getUserByUsername(
      @PathVariable String username) {
    UserSignInResponseDto responseDto = userService.getUserByUsername(username);
    return ResponseEntity.ok().body(responseDto);
  }
}
