package com.salaryc.user.user.dto

import com.salaryc.user.user.entity.User

data class UserRequest(
    val email : String,
    val password : String
) {

}