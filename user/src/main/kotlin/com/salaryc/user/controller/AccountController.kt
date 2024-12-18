package com.salaryc.user.controller

import com.salaryc.user.dto.RegisterRequestDto
import com.salaryc.user.dto.RegisterResponseDto
import com.salaryc.user.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class AccountController(
    private val accountService: AccountService
) {

    @PostMapping
    fun createUser(@RequestBody registerRequestDto: RegisterRequestDto): ResponseEntity<RegisterResponseDto>{
        return ResponseEntity.ok(accountService.create(registerRequestDto))
    }

//    @GetMapping
//    fun findUser(request: ServletRequest, response:ServletResponse): ResponseEntity<UserResponseDto>{
//
//    }
//
//    @DeleteMapping
//    fun deleteUser(): ResponseEntity<Void>{
//        userService.delete()
//
//    }
}