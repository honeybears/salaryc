package com.salaryc.auth.security.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisService(
    private val redisTemplate: RedisTemplate<String, Any>
) {

    fun save(key: String, value: String) {
        redisTemplate.opsForValue().set(key, value)
    }

    fun find(key: String): String? {
        return redisTemplate.opsForValue().get(key) as? String
    }

    fun delete(key: String) {
        redisTemplate.delete(key)
    }
}