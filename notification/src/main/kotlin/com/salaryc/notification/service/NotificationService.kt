package com.salaryc.notification.service

import com.salaryc.notification.dto.NotificationRequestDto
import com.salaryc.notification.entity.Notification
import com.salaryc.notification.repository.MessageTargetRepository
import com.salaryc.notification.repository.NotificationRepository
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val messageTargetRepository: MessageTargetRepository,
    private val notificationRepository: NotificationRepository,
) {

    @KafkaListener(topics = ["notification-topic"], groupId = "notification-group")
    fun processNotification(message: NotificationRequestDto) {
        println("Received notification: $message")
        // 서비스 로직 수행
        sendNotification(message)
    }



    private fun sendNotification(notificationRequestDto: NotificationRequestDto) {

        val messageTarget = messageTargetRepository.findByUserId(notificationRequestDto.userId)

        val notification = Notification(
            targetUser = messageTarget,
            message = notificationRequestDto.message
        )
        notificationRepository.save(notification)
        // 실제 비즈니스 로직을 처리하는 코드
        println("Handling notification for user: ${notificationRequestDto.userId}")
    }

}