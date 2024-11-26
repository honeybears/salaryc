package com.salaryc.user.user.service

import com.salaryc.user.security.JwtTokenProvider
import com.salaryc.user.security.SecurityUtils
import com.salaryc.user.user.dto.RegisterRequestDto
import com.salaryc.user.user.dto.RegisterResponseDto
import com.salaryc.user.user.dto.UserResponseDto
import com.salaryc.user.user.entity.User
import com.salaryc.user.user.entity.UserRole
import com.salaryc.user.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    @Transactional(readOnly = true)
    fun getUser() : UserResponseDto{
        val user = SecurityUtils.getAuthenticatedUser()
        return UserResponseDto(
            id = user.id!! ,
            email = user.email,
        )
    }
    @Transactional(readOnly = false)
    fun createEmployeeUser(registerRequest: RegisterRequestDto): RegisterResponseDto{

        val user = User(
            email = registerRequest.email,
            password = passwordEncoder.encode(registerRequest.password),
            role = UserRole.EMPLOYEE_ACCOUNT
        )
        userRepository.save(user)
        return RegisterResponseDto(
            id = user.id!!,
            email = user.email,
            accessToken = SecurityUtils.createAccessTokenWithUserInfo(user),
            refreshToken = SecurityUtils.createRefreshTokenWithUserInfo(user)
        )
    }
}