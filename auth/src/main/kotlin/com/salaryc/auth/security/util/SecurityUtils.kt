package com.salaryc.auth.security.util

import com.salaryc.auth.security.vo.TokenInfo
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class SecurityUtils {

    @Value("\${jwt.secret}")
    private lateinit var secretKey: String
    private val key: Key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)) }

    companion object{
        private const val accessTokenExpiration = 1000 * 60 * 60 * 24 * 1 // 1일
        private const val refreshTokenExpiration = 1000 * 60 * 60 * 24 * 7 // 7일
    }


    fun createAccessTokenWithUserInfo(userId: Long, userRole: String): String {

        val now = Date()

        val accessToken =
            Jwts.builder()
                .setSubject(userId.toString())
                .addClaims(mapOf("role" to userRole))
                .setIssuedAt(now)
                .setExpiration(Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(key, io.jsonwebtoken.SignatureAlgorithm.HS256).compact()
        return accessToken
    }

    fun createRefreshTokenWithUserInfo(userId: Long, userRole: String): String {

        val now = Date()

        val refreshToken =
            Jwts.builder()
                .setSubject(userId.toString())
                .addClaims(mapOf("role" to userRole))
                .setIssuedAt(now)
                .setExpiration(Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(key, io.jsonwebtoken.SignatureAlgorithm.HS256).compact()
        return refreshToken
    }

    fun decryptToken(token: String): TokenInfo {

        validateToken(token)
        val parsedToken = extractBearerToken(token)

        val claims: Claims = Jwts
            .parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(parsedToken)
            .body

        val auth = claims["role"] ?: throw RuntimeException("잘못된 토큰입니다.")
        return TokenInfo(
            userId = claims.subject.toLong(),
            userRole = auth.toString()
        )
    }

    private fun validateToken(token: String): Boolean {
        val parsedToken = extractBearerToken(token)
        try {
            getClaims(parsedToken)
            return true
        } catch (e: Exception) {
            println(e.message)
        }
        return false
    }

    private fun extractBearerToken(header: String?): String {
        if (header == null || !header.startsWith("Bearer ")) {
            throw IllegalArgumentException("Authorization 헤더가 잘못되었습니다.")
        }
        return header.substringAfter("Bearer ").trim()
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
    }
}
