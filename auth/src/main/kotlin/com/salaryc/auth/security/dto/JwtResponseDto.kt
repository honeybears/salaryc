package com.salaryc.auth.security.dto

data class JwtResponseDto(
    val accessToken: String,
    val refreshToken: String
) {
}