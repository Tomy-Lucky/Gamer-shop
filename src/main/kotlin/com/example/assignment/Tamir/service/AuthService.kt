package com.example.assignment.Tamir.service

import com.example.assignment.Tamir.web.jwt.JWTService
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtService: JWTService,
    private val accountService: AccountService
) {

    fun authByUserNameAndPassword(userName: String, password: String): String {
        val account = accountService.findByNameAndPassword(userName = userName, password = password)
        return jwtService.sign(accountId = account.id)
    }
}
