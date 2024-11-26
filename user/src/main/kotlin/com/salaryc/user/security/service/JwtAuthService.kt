package com.salaryc.user.security.service

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service

@Service
class JwtAuthService : AuthService {
    fun validateToken(httpServletRequest: HttpServletRequest){
        val jwtAuthToken = httpServletRequest.getHeader("Authorization")

    }
}