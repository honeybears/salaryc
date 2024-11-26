package com.salaryc.user.user.dto

data class AuthTokenRequestDto(
    val userId : Long,
    val userRole : String
)
