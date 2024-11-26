package com.salaryc.auth.security.service

import com.salaryc.auth.security.dto.JwtResponseDto
import com.salaryc.auth.security.dto.UserInfoRequestDto
import com.salaryc.auth.security.exception.RefreshTokenMissMatchException
import com.salaryc.auth.security.exception.RefreshTokenNotFoundException
import com.salaryc.auth.security.util.SecurityUtils
import org.springframework.stereotype.Service

@Service
class JwtAuthService(
    private val redisService: RedisService,
    private val securityUtils: SecurityUtils
) : AuthService {

    fun authJwtToken(token : String) : UserInfoRequestDto {
        val tokenInfo = securityUtils.decryptToken(token)
        return UserInfoRequestDto(
            userId = tokenInfo.userId,
            userRole = tokenInfo.userRole
        )
    }

    fun generateAccessToken(userInfoRequestDto: UserInfoRequestDto) : JwtResponseDto{
        val accessToken = securityUtils.createAccessTokenWithUserInfo(userId = userInfoRequestDto.userId, userRole = userInfoRequestDto.userRole)
        val refreshToken = securityUtils.createRefreshTokenWithUserInfo(userId = userInfoRequestDto.userId, userRole = userInfoRequestDto.userRole)

        redisService.save("refreshToken:${userInfoRequestDto.userId}", refreshToken)

        return JwtResponseDto(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun refreshAccessToken(refreshToken: String) : JwtResponseDto{
        val tokenInfo = securityUtils.decryptToken(refreshToken)

        val refreshTokenData = redisService.find("refreshToken:${tokenInfo.userId}") ?: throw RefreshTokenNotFoundException()

        if(refreshToken.substring(7).trim() != refreshTokenData){
            throw RefreshTokenMissMatchException()
        }

        val newAccessToken = securityUtils.createAccessTokenWithUserInfo(userId = tokenInfo.userId, userRole = tokenInfo.userRole)
        val newRefreshToken = securityUtils.createRefreshTokenWithUserInfo(userId = tokenInfo.userId, userRole = tokenInfo.userRole)

        redisService.save("refreshToken:${tokenInfo.userId}", newRefreshToken)

        return JwtResponseDto(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }

}