package com.salaryc.gateway.filter

import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthFilter(
    builder: WebClient.Builder
) : GlobalFilter, Ordered {

    private val webClient: WebClient = builder.baseUrl("http://localhost:8004").build() // Auth 서버 URL

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val path = exchange.request.uri.path

        // 인증 생략 경로
        val excludedPaths = setOf(
            "/api/users/register",
            "/api/users/login",
            "/api/auth"
        )

        // 인증이 필요 없는 경로는 필터 생략
        if (excludedPaths.contains(path)) {
            return chain.filter(exchange)
        }

        val token = exchange.request.headers.getFirst("Authorization")
        return if (token != null && token.startsWith("Bearer ")) {
            val strippedToken = token.substring(7).trim() // "Bearer " 제거

            // Auth 서버로 검증 요청
            webClient.get()
                .uri("/api/auth")
                .header("Authorization", "Bearer $strippedToken")
                .retrieve()
                .bodyToMono(AuthResponse::class.java)
                .flatMap { authResponse ->
                    // 인증 성공 시 사용자 정보 및 헤더 추가
                    val request = exchange.request.mutate()
                        .header("X-Request-Authenticated", "true")
                        .header("X-User-Id", authResponse.userId.toString())
                        .header("X-User-Roles", authResponse.userRole)
                        .build()
                    chain.filter(exchange.mutate().request(request).build())
                }
                .onErrorResume {
                    // 인증 실패 시 UNAUTHORIZED 반환
                    exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                    exchange.response.setComplete()
                }
        } else {
            // 토큰이 없거나 형식이 잘못된 경우
            exchange.response.statusCode = HttpStatus.UNAUTHORIZED
            exchange.response.setComplete()
        }
    }
    override fun getOrder(): Int = -1 // 필터 우선순위
}

data class AuthResponse(
    val userId: Long,
    val userRole: String
)
