package com.salaryc.user.user.dto

data class AuthTokenResponseDto(
    val accessToken: String,
    val refreshToken: String
) {
}