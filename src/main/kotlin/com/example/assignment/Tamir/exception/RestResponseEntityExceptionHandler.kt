package com.example.assignment.Tamir.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler
    fun handle(e: Exception): ResponseEntity<String> = when (e) {
        is CardNumberAlreadyExistsException -> ResponseEntity(e.localizedMessage, HttpStatus.BAD_REQUEST)
        is CardNumberNotFoundByNumberException -> ResponseEntity(e.localizedMessage, HttpStatus.NOT_FOUND)
        is IncorrectPinCodeException -> ResponseEntity(e.localizedMessage, HttpStatus.NOT_FOUND)
        is CardNumberNotFoundByIDException -> ResponseEntity(e.localizedMessage, HttpStatus.NOT_FOUND)
        is IncorrectWithdrawAmount -> ResponseEntity(e.localizedMessage, HttpStatus.BAD_REQUEST)
        is NegativeAmountException -> ResponseEntity(e.localizedMessage, HttpStatus.BAD_REQUEST)
        else -> ResponseEntity(e.localizedMessage, HttpStatus.UNAUTHORIZED)
    }
}

