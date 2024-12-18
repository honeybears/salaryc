package com.salaryc.salary.controller

import com.salaryc.salary.dto.NotificationRequestDto
import com.salaryc.salary.service.KafkaProducer
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/kafka")
class KafkaController(
    private val kafkaProducer: KafkaProducer
) {

    @PostMapping("/publish")
    fun publishMessage(@RequestBody payload: NotificationRequestDto) {
        kafkaProducer.sendMessage("test-topic", payload)
        println("Published message: $payload")
    }
}