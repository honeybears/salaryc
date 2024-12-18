package com.salaryc.user.service

import com.salaryc.user.dto.AuthTokenRequestDto
import com.salaryc.user.dto.AuthTokenResponseDto
import com.salaryc.user.dto.RegisterRequestDto
import com.salaryc.user.dto.RegisterResponseDto
import com.salaryc.user.dto.*

import com.salaryc.user.entity.Account
import com.salaryc.user.entity.AccountRole
import com.salaryc.user.repository.AccountRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
    private val gatewayWebClient: WebClient
) {

    fun create(registerRequestDto: RegisterRequestDto): RegisterResponseDto {
        val account = accountRepository.save(
            Account(
                phone = registerRequestDto.phone,
                password = passwordEncoder.encode(registerRequestDto.password),
                role = AccountRole.fromRoleName(registerRequestDto.role)
            )
        )

        val tokens = gatewayWebClient.post()
            .uri("/api/auth")
            .bodyValue(
                AuthTokenRequestDto(
                    userId = account.id!!,
                    userRole = account.role.roleName
                )
            )
            .retrieve()
            .bodyToMono(AuthTokenResponseDto::class.java)
            .block() ?: throw RuntimeException("Auth Server 응답 오류")

        return RegisterResponseDto(
            id = account.id!!,
            phone = account.phone,
            accessToken = tokens.accessToken,
            refreshToken = tokens.refreshToken
        )

    }

    fun find(userId: Long): AccountResponseDto {



        val user = accountRepository.findById(userId).get()

        return AccountResponseDto(
            id = user.id!!,
            phone = user.phone
        )
    }

    fun delete(userId: Long) {
        accountRepository.deleteById(userId)
    }

}