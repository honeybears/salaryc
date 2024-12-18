package com.salaryc.user.dto

data class AuthTokenRequestDto(
    val userId : Long,
    val userRole : String
)
