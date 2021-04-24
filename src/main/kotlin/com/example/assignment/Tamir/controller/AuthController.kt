package com.example.assignment.Tamir.controller

import com.example.assignment.Tamir.service.AuthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @GetMapping("/by-password")
    fun getBookDetails(
        @RequestParam("userName", required = true) userName: String,
        @RequestParam("password", required = true) password: String
    ) = authService.authByUserNameAndPassword(
        userName = userName,
        password = password
    )
}
