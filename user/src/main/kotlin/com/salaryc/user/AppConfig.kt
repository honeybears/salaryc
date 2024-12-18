package com.salaryc.user

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class AppConfig {
    @Value("\${gateway.url}")
    private lateinit var apiGatewayUrl: String

    @Bean
    fun gatewayWebClient() : WebClient {
        return WebClient.builder().baseUrl(apiGatewayUrl).build()
    }

    @Bean
    fun passwordEncryptor(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}