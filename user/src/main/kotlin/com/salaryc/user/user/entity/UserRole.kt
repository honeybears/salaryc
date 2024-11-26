package com.salaryc.user.user.entity

enum class UserRole(val roleName: String) {
    PRESIDENT_ACCOUNT("president"),
    EMPLOYEE_ACCOUNT("employee"),
    ADMIN_ACCOUNT("admin");
    companion object {
        fun fromRoleName(roleName: String): UserRole {
            return entries.find { it.roleName == roleName.lowercase() }
                ?: throw IllegalArgumentException("Invalid role name: $roleName")
        }
    }
}