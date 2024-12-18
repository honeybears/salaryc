package com.salaryc.notification.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisRepository(
    private val redisTemplate: RedisTemplate<String,Any>
) {
    fun save(key:String, value: Any){
        redisTemplate.opsForValue().set(key, value)
    }

    fun <T> find(key:String, clazz: Class<T>) : T?{
        val value = redisTemplate.opsForValue().get(key)
        return clazz.cast(value)
    }

    fun delete(key: String){
        redisTemplate.opsForValue().getAndDelete(key)
    }

    fun update(key:String, value:Any){
        redisTemplate.opsForValue().set(key, value)
    }

    fun exists(key: String): Boolean {
        return redisTemplate.hasKey(key) ?: false
    }
}