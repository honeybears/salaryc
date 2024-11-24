package com.salaryc.user.entity

import jakarta.persistence.*

@Entity
@Table(name = "`user`")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long? = null,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val employees: MutableList<Employee> = mutableListOf()
) {
}