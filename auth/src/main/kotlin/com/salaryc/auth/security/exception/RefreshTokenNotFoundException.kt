package com.salaryc.auth.security.exception

class RefreshTokenNotFoundException : RuntimeException() {
    override val message: String?
        get() = super.message
}