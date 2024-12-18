package com.salaryc.notification.dto

data class NotificationRequestDto(
    val userId: Long,
    val message: String,
    val timestamp: Long
)
