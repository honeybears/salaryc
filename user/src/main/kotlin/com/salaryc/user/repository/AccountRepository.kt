package com.salaryc.user.repository

import com.salaryc.user.entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account,Long> {
}