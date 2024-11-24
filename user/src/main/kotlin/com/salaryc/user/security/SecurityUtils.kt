package com.salaryc.user.security

import com.salaryc.user.user.entity.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class SecurityUtils {

    @Value("\${jwt.secret}")
    lateinit var secretKey: String
    private val key: Key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)) }

    private val accessTokenExpiration = 1000 * 60 * 15 // 15분
    private val refreshTokenExpiration = 1000 * 60 * 60 * 24 * 7 // 7일

    // 현재 인증된 사용자 가져오기
    fun getAuthenticatedUser(): User? {
        val principal = SecurityContextHolder.getContext().authentication.principal
        return if (principal is CustomUserDetails) {
            principal.getUser()
        } else {
            null
        }
    }

    // 액세스 토큰 생성
    fun generateAccessToken(user: User): String {
        return Jwts.builder()
            .setSubject(user.id.toString())
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + accessTokenExpiration))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    // 리프레시 토큰 생성
    fun generateRefreshToken(user: User): String {
        return Jwts.builder()
            .setSubject(user.id.toString())
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + refreshTokenExpiration))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    // 토큰 유효성 검증
    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            true
        } catch (e: Exception) {
            println("Invalid token: ${e.message}") // 디버깅용 로그, 프로덕션에서는 제거
            false
        }
    }

    // 토큰에서 사용자 ID 추출
    fun extractUserId(token: String): String? {
        return try {
            val claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
            claims.subject
        } catch (e: Exception) {
            println("Failed to extract user ID: ${e.message}") // 디버깅용 로그, 프로덕션에서는 제거
            null
        }
    }
}
