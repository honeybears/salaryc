    package com.salaryc.auth.security.controller

    import com.salaryc.auth.security.dto.JwtResponseDto
    import com.salaryc.auth.security.dto.UserInfoRequestDto
    import com.salaryc.auth.security.service.AuthService
    import com.salaryc.auth.security.service.JwtAuthService
    import jakarta.servlet.http.HttpServletRequest
    import jakarta.servlet.http.HttpServletResponse
    import org.springframework.http.ResponseEntity
    import org.springframework.web.bind.annotation.GetMapping
    import org.springframework.web.bind.annotation.PatchMapping
    import org.springframework.web.bind.annotation.PostMapping
    import org.springframework.web.bind.annotation.RequestBody
    import org.springframework.web.bind.annotation.RequestMapping
    import org.springframework.web.bind.annotation.RestController

    @RequestMapping("/api/auth")
    @RestController
    class AuthController(
        private val jwtAuthService: AuthService
    ) {
        @GetMapping("/hi")
        fun hi(): ResponseEntity<String>{
            return ResponseEntity.ok("hi")
        }

        @GetMapping
        fun getJwtAuthorization(request: HttpServletRequest, response: HttpServletResponse) : ResponseEntity<Void>{
            val authToken = request.getHeader("Authorization")
            val tokens = jwtAuthService.authJwtToken(authToken)
            response.addHeader("X-USER-ID", tokens.userId.toString())
            response.addHeader("X-USER-ROLE", tokens.userRole)
            return ResponseEntity.ok().build()
        }

        @PostMapping
        fun generateAccessToken(@RequestBody userInfoRequestDto: UserInfoRequestDto) : ResponseEntity<JwtResponseDto>{
            val tokens = jwtAuthService.generateAccessToken(userInfoRequestDto)
            return ResponseEntity.ok(tokens)
        }

        @PatchMapping
        fun refreshAccessToken(request: HttpServletRequest, response: HttpServletResponse) : ResponseEntity<JwtResponseDto>{
            val refreshToken = request.getHeader("Refresh")
            val token = jwtAuthService.refreshAccessToken(refreshToken)
            return ResponseEntity.ok(token)
        }
    }