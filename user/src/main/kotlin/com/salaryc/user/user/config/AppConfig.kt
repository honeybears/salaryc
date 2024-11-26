package com.salaryc.user.user.config

import com.salaryc.user.user.repository.UserRepository
import com.salaryc.user.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class AppConfig {
    @Value("\${auth-service.url}")
    private lateinit var authServerUrl: String

    @Autowired
    private lateinit var userRepository: UserRepository

    @Bean
    fun authWebClient() : WebClient {
        return WebClient.builder().baseUrl(authServerUrl).build()
    }
    @Bean
    fun userService() : UserService{
        return UserService(
            userRepository = userRepository,
            authWebClient =  authWebClient(),
            passwordEncoder = passwordEncryptor()
        )
    }
    @Bean
    fun passwordEncryptor(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}