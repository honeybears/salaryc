package com.salaryc.user.user.repository

import com.salaryc.user.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User,Long> {
    fun findByEmail(email:String): User?
}