package com.salaryc.user.user.controller

import com.salaryc.user.user.dto.RegisterRequestDto
import com.salaryc.user.user.dto.RegisterResponseDto
import com.salaryc.user.user.dto.UserResponseDto
import com.salaryc.user.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
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
    fun createEmployeeUser(@RequestBody userRequest: RegisterRequestDto) : ResponseEntity<RegisterResponseDto> {
        val userResponse = userService.createEmployeeUser(userRequest)
        return ResponseEntity.ok(userResponse)
    }
    @GetMapping
    fun getUser(): ResponseEntity<UserResponseDto>{
        val userResponse = userService.getUser()
        return ResponseEntity.ok(userResponse)
    }
}