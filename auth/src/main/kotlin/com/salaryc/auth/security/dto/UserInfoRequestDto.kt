package com.salaryc.auth.security.dto

data class UserInfoRequestDto(
    val userId : Long,
    val userRole : String
) {
}