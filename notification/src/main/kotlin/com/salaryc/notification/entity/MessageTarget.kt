package com.salaryc.notification.entity

import jakarta.persistence.*

@Entity
@Table(name = "message_target")
class MessageTarget(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id:Long? = null,
    @Column(name = "user_id")
    private val userId : Long
) {
}