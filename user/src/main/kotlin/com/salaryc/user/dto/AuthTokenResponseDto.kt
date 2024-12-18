package com.salaryc.user.dto

data class AuthTokenResponseDto(
    val accessToken: String,
    val refreshToken: String
) {
}