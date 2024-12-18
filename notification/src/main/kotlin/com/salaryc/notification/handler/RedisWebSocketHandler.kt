package com.salaryc.notification.handler

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap

@Component
class RedisWebSocketHandler(
    private val redisTemplate: RedisTemplate<String, String>
) : TextWebSocketHandler() {

}
