package com.salaryc.salary.dto

data class NotificationRequestDto(
    val userId: String,
    val message: String,
    val timestamp: Long
)
