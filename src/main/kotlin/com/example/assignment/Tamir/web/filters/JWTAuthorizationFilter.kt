package com.example.assignment.Tamir.web.filters

import com.example.assignment.Tamir.web.jwt.JWTService
import com.auth0.jwt.exceptions.JWTVerificationException
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class JWTAuthorizationFilter(
    private val jwtService: JWTService
) : Filter {

    private companion object {
        const val TOKEN_PREFIX = "Bearer "
        val exceptionRoutes = listOf(
            "/auth/.*",
        )
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val req = request as HttpServletRequest
        val currentRoute = req.servletPath

        if (exceptionRoutes.any { route -> currentRoute.matches(route.toRegex()) }) {
            chain.doFilter(request, response)
            return
        }

        val headerValue = req.getHeader(HttpHeaders.AUTHORIZATION)
        if (headerValue === null || !headerValue.startsWith(TOKEN_PREFIX))
            return respondError(response = response, message = "BEARER_TOKEN_NOT_SET")

        try {
            val accountId = jwtService.getSub(headerValue.replace(TOKEN_PREFIX, ""))
            if (accountId === null)
                return respondError(response = response, message = "INVALID_TOKEN")
        } catch (e: Exception) {
            when (e) {
                is JWTVerificationException -> return respondError(response, "INVALID_TOKEN")
                else -> throw e
            }
        }

        chain.doFilter(request, response)
    }

    private fun respondError(response: ServletResponse, message: String) {
        response as HttpServletResponse
        response.status = 401
        response.addHeader(HttpHeaders.CONTENT_TYPE, "application/json")
        response.writer.write(message)
        return
    }
}
