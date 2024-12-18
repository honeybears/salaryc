package com.salaryc.auth.security.service

import com.salaryc.auth.security.dto.JwtResponseDto
import com.salaryc.auth.security.dto.UserInfoRequestDto

interface AuthService {
    fun authJwtToken(token: String):UserInfoRequestDto
    fun generateAccessToken(userInfoRequestDto: UserInfoRequestDto):JwtResponseDto
    fun refreshAccessToken(refreshToken: String):JwtResponseDto
}