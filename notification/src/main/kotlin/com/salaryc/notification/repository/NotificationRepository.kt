package com.salaryc.notification.repository

import com.salaryc.notification.entity.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository : JpaRepository<Notification,Long>{
}