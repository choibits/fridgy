package com.fridgy.app.service;

import com.fridgy.app.dto.AuthRequestDto;
import com.fridgy.app.dto.AuthResponseDto;
import com.fridgy.app.model.User;

public interface IAuthService {
    AuthResponseDto signup(AuthRequestDto requestDto);
    AuthResponseDto login(AuthRequestDto requestDto);
}
