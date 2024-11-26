package com.salaryc.auth.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    @Bean
    fun jedisConnectionFactory(): JedisConnectionFactory {
        val redisConfig = RedisStandaloneConfiguration()
        return JedisConnectionFactory(redisConfig)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.connectionFactory = jedisConnectionFactory()
        template.keySerializer = StringRedisSerializer() // 문자열 키 직렬화
        template.valueSerializer = StringRedisSerializer() // 문자열 값 직렬화
        return template
    }
}