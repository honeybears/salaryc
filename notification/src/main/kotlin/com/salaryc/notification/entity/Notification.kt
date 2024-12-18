package com.salaryc.notification.entity

import jakarta.persistence.*

@Entity
@Table(name = "notification")
class Notification(
    @Id
    @GeneratedValue
    private var id:Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id")
    private val targetUser : MessageTarget,

    @Column(name = "message")
    private val message: String
) {

}