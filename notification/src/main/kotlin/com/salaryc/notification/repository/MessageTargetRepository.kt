package com.salaryc.notification.repository

import com.salaryc.notification.entity.MessageTarget
import org.springframework.data.jpa.repository.JpaRepository

interface MessageTargetRepository : JpaRepository<MessageTarget,Long> {
    fun findByUserId(userId:Long):MessageTarget
}