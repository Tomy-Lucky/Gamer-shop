package com.example.assignment.Tamir.web.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Date
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class JWTService(
    @Value("\${app.jwt.secret}") private val secret: String,
    @Value("\${app.jwt.expirationInMinutes}") private val expirationInMinutes: Long
) {
    private val algorithm = Algorithm.HMAC512(secret)

    fun getSub(token: String) = JWT.require(algorithm)
        .build()
        .verify(token)
        .subject
        ?.toLong()

    fun sign(accountId: Long): String {
        val localDateTime = LocalDateTime.now().plus(Duration.of(expirationInMinutes, ChronoUnit.MINUTES))
        val expirationDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())

        return JWT.create()
            .withIssuer("")
            .withExpiresAt(expirationDate)
            .withSubject(accountId.toString())
            .sign(algorithm)
    }
}
