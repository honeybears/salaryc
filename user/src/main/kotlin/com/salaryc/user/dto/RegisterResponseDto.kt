package com.salaryc.user.dto

data class RegisterResponseDto(
    val id : Long,
    val phone : String,
    val accessToken : String,
    val refreshToken : String
)