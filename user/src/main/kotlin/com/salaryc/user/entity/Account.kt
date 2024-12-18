package com.salaryc.user.entity

import com.salaryc.user.base.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "`user`")
class Account(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var phone : String,

    var password: String,

    @Enumerated(EnumType.STRING)
    val role: AccountRole,


    ) : BaseEntity()