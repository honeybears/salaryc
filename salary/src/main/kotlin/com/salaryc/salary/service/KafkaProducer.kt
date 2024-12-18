package com.salaryc.salary.service

import com.salaryc.salary.dto.NotificationRequestDto
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, NotificationRequestDto>
) {

    fun sendMessage(topic: String, payload: NotificationRequestDto) {
        kafkaTemplate.send(topic, payload)
        println("Message sent to topic $topic: $payload")
    }
}