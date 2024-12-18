package com.salaryc.user.entity

enum class AccountRole(val roleName: String) {
    PRESIDENT_ACCOUNT("president"),
    EMPLOYEE_ACCOUNT("employee"),
    ADMIN_ACCOUNT("admin");
    companion object {
        fun fromRoleName(roleName: String): AccountRole {
            return entries.find { it.roleName == roleName.lowercase() }
                ?: throw IllegalArgumentException("Invalid role name: $roleName")
        }
    }
}