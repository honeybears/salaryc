package com.salaryc.user.security

class UserNotFoundException : RuntimeException() {
    override val message: String
        get() = "유저를 찾을 수 없습니다."
}