package com.salaryc.auth.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@EnableRedisRepositories
class RedisConfig {

    @Bean
    fun jedisConnectionFactory(): JedisConnectionFactory {
        // Redis 서버 연결 설정
        val redisConfig = RedisStandaloneConfiguration()
        redisConfig.hostName = "auth-redis" // Redis 컨테이너 이름
        redisConfig.port = 6379 // Redis 컨테이너 외부 포트
        return JedisConnectionFactory(redisConfig)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.connectionFactory = jedisConnectionFactory() // Redis 연결 팩토리 설정
        template.keySerializer = StringRedisSerializer() // 문자열 키 직렬화
        template.valueSerializer = StringRedisSerializer() // 문자열 값 직렬화
        return template
    }
}