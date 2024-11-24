package com.salaryc.user.user.service

import com.salaryc.user.user.dto.UserRequest
import com.salaryc.user.user.dto.UserResponse
import com.salaryc.user.user.entity.User
import com.salaryc.user.user.entity.UserRole
import com.salaryc.user.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository
) {
    @Transactional(readOnly = false)
    fun createEmployeeUser(userRequest: UserRequest): UserResponse{
        val user = User(
            email = userRequest.email,
            password = userRequest.password,
            role = UserRole.EMPLOYEE_ACCOUNT
        )
        userRepository.save(user)
        return UserResponse(
            id = user.id!!,
            password = user.password,
            email = user.email
        )
    }
}