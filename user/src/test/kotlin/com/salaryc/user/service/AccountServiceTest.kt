package com.salaryc.user.service

import com.salaryc.user.dto.RegisterRequestDto
import com.salaryc.user.entity.Account
import com.salaryc.user.entity.AccountRole
import com.salaryc.user.repository.AccountRepository
import io.mockk.every
import io.mockk.mockk
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient
import kotlin.test.assertEquals

class AccountServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var accountService: AccountService

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val gatewayWebClient = WebClient.builder()
            .baseUrl(mockWebServer.url("/").toString())
            .build()
        val mockAccountRepository = mockk<AccountRepository>()

        every { mockAccountRepository.save(any()) } returns Account(
            id = 1L,
            password = "password",
            phone = "000-0000-0000",
            role = AccountRole.PRESIDENT_ACCOUNT
        )
        accountService = AccountService(
            accountRepository = mockAccountRepository,
            passwordEncoder = mockk(relaxed = true),
            gatewayWebClient = gatewayWebClient
        )
    }

    @AfterEach
    fun tearDown() {
        // MockWebServer 종료
        mockWebServer.shutdown()
    }

    @Test
    fun `should create user and retrieve tokens`() {
        val mockResponse = MockResponse()
            .setBody("""
                {
                    "accessToken": "mock-access-token",
                    "refreshToken": "mock-refresh-token"
                }
            """)
            .setResponseCode(200)
            .addHeader("Content-Type", "application/json")

        mockWebServer.enqueue(mockResponse)

        val registerRequestDto = RegisterRequestDto(
            phone = "1234567890",
            password = "password123",
            role = "employee"
        )

        val response = accountService.create(registerRequestDto)

        assertEquals("mock-access-token", response.accessToken)
        assertEquals("mock-refresh-token", response.refreshToken)
    }
}
