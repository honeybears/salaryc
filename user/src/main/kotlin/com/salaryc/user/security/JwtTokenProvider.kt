package com.salaryc.user.security

import com.salaryc.user.user.entity.User
import com.salaryc.user.user.entity.UserRole
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider {
    @Value("\${jwt.secret}")
    lateinit var secretKey: String
    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)) }

    fun createTokenWithUserInfo(user: User): String {

        val userId = user.id.toString()
        val authorities = user.email

        val now = Date()

        val accessToken = Jwts.builder()
            .setSubject(userId)
            .addClaims(mapOf("auth" to authorities, "role" to user.role.roleName))
            .setIssuedAt(now)
            .signWith(key, io.jsonwebtoken.SignatureAlgorithm.HS256)
            .compact()
        return "Bearer $accessToken"
    }
    fun getAuthentication(token: String): Authentication {
        val claims: Claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

        // auth와 userId를 추출
        val auth = claims["auth"] ?: throw RuntimeException("잘못된 토큰입니다.")
        val userId = claims["userId"] as? Long
            ?: throw RuntimeException("userId가 올바르지 않습니다.")

        // 권한 목록 생성
        val authorities: Collection<GrantedAuthority> = (auth as String)
            .split(",")
            .map { SimpleGrantedAuthority(it.trim()) }

        if(authorities.isEmpty())
            throw RuntimeException("아무 권한이 없습니다.")
        // User 객체 생성 (여기서는 예시로 간단히 만듦)
        val user = User(
            id = userId,
            email = claims.subject, // JWT의 subject를 이메일로 사용
            password = "", // JWT에는 일반적으로 비밀번호가 포함되지 않음
            role = UserRole.valueOf(authorities.first().toString()) // 첫 번째 권한을 역할로 사용
        )

        // CustomUserDetails를 생성
        val userDetails = CustomUserDetails(user)

        // UsernamePasswordAuthenticationToken 반환
        return UsernamePasswordAuthenticationToken(userDetails, null, authorities)
    }
    fun validateToken(token: String): Boolean {
        try {
            getClaims(token)
            return true
        } catch (e: Exception) {
            println(e.message)
        }
        return false
    }

    private fun getClaims(token: String): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }
}