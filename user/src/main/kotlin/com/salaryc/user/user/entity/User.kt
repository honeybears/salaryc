package com.salaryc.user.user.entity

import com.salaryc.user.base.BaseEntity
import com.salaryc.user.employee.entity.Employee
import com.salaryc.user.president.entity.President
import jakarta.persistence.*

@Entity
@Table(name = "`user`")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var email: String,

    var password: String,

    @Enumerated(EnumType.STRING)
    val role: UserRole,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val employees: MutableList<Employee> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val president: MutableList<President> = mutableListOf()

) : BaseEntity()