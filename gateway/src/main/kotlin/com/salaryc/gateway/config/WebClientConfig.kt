//package com.salaryc.gateway.config
//import com.salaryc.gateway.filter.AuthFilter
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.web.reactive.function.client.WebClient
//
//@Configuration
//class WebClientConfig {
//
//    @Bean
//    fun authWebClient(): WebClient {
//        return WebClient.builder().baseUrl("http://localhost:8004").build()
//    }
//    @Bean
//    fun authFilter(): AuthFilter{
//        return AuthFilter(authWebClient())
//    }
//}