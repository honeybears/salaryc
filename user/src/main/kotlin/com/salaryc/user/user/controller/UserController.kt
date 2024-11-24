package com.salaryc.user.user.controller

import com.salaryc.user.user.dto.UserRequest
import com.salaryc.user.user.dto.UserResponse
import com.salaryc.user.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @PostMapping
    fun createEmployeeUser(@RequestBody userRequest: UserRequest) : ResponseEntity<UserResponse>{
        val userResponse = userService.createEmployeeUser(userRequest)
        return ResponseEntity.ok(userResponse)
    }
}