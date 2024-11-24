package com.salaryc.user.employee.entity

import com.salaryc.user.base.BaseEntity
import com.salaryc.user.user.entity.User
import jakarta.persistence.*

@Entity
@Table(name = "employee")
class Employee(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user : User

) : BaseEntity()