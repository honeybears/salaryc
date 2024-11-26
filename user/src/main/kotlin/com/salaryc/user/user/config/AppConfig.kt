package com.salaryc.user.user.config

import com.salaryc.user.user.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceConfig {
    @Bean
    fun userService() : UserService{
        return UserService()
    }
}