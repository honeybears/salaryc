package com.salaryc.user.user.service

import com.salaryc.user.user.dto.AuthTokenRequestDto
import com.salaryc.user.user.dto.AuthTokenResponseDto
import com.salaryc.user.user.dto.RegisterRequestDto
import com.salaryc.user.user.dto.RegisterResponseDto

import com.salaryc.user.user.entity.User
import com.salaryc.user.user.entity.UserRole
import com.salaryc.user.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authWebClient: WebClient
) {
//    fun createUser(registerRequestDto: RegisterRequestDto) : RegisterResponseDto{
//        val user = userRepository.save(
//            User(
//                phone = registerRequestDto.phone,
//                password = passwordEncoder.encode(registerRequestDto.password),
//                role = UserRole.fromRoleName(registerRequestDto.role)
//            )
//        )
//
//        authWebClient.post()
//            .uri("/api/auth")
//            .bodyValue(AuthTokenRequestDto(
//                userId = user.id!!,
//                userRole = user.role.roleName
//            ))
//            .retrieve()
//            .bodyToMono(AuthTokenResponseDto::class.java)
//            .
//
//
//
//    }

}