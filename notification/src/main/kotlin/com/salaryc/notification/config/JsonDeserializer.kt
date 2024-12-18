package com.salaryc.notification.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.apache.kafka.common.serialization.Deserializer

class JsonDeserializer<T> : Deserializer<T> {
    private val objectMapper = ObjectMapper().registerKotlinModule()
    private lateinit var targetClass: Class<T>

    constructor()

    // 대상 클래스 설정
    constructor(targetClass: Class<T>) {
        this.targetClass = targetClass
    }
    override fun deserialize(topic: String, data: ByteArray): T {
        return objectMapper.readValue(data, targetClass)
    }
}
