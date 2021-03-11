package com.example.assignment.Tamir.controller

import com.example.assignment.Tamir.dto.AccountDTO
import com.example.assignment.Tamir.service.AccountService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/account")
class AccountController(
    private val accountService: AccountService
) {

    @GetMapping("/users")
    fun getUsers() = accountService.findAll()

    @GetMapping("/add/user-card")
    fun createUserCard(
        @RequestParam("userName", required = true) userName: String,
        @RequestParam("cardNumber", required = true) cardNumber: String,
        @RequestParam("pinCode", required = true) pinCode: String,
        @RequestParam("balance", required = true) balance: BigDecimal
    ) = accountService.createAccount(
        accountDTO = AccountDTO(
            id = 0,
            userName = userName,
            cardNumber = cardNumber,
            pinCode = pinCode,
            balance = balance
        )
    )

    @GetMapping("/change-pinCode")
    fun changePinCode(
        @RequestParam("cardNumber", required = true) cardNumber: String,
        @RequestParam("pinCode", required = true) pinCode: String,
        @RequestParam("newPinCode", required = true) newPinCode: String
    ) = accountService.changePinCode(
        cardNumber = cardNumber,
        pinCode = pinCode,
        newPinCode = newPinCode
    )

    @GetMapping("/see-balance")
    fun seeBalance(
        @RequestParam("cardNumber", required = true) cardNumber: String,
        @RequestParam("pinCode", required = true) pinCode: String
    ) = accountService.getAccountByPinCode(
        cardNumber = cardNumber,
        pinCode = pinCode
    )

    @GetMapping("/withdraw")
    fun withdraw(
        @RequestParam("cardNumber", required = true) cardNumber: String,
        @RequestParam("pinCode", required = true) pinCode: String,
        @RequestParam("amount", required = true) amount: BigDecimal
    ) = accountService.withdraw(
        cardNumber = cardNumber,
        pinCode = pinCode,
        amount = amount
    )

    @GetMapping("/top-up")
    fun topUp(
        @RequestParam("cardNumber", required = true) cardNumber: String,
        @RequestParam("pinCode", required = true) pinCode: String,
        @RequestParam("amount", required = true) amount: BigDecimal
    ) = accountService.topUp(
        cardNumber = cardNumber,
        pinCode = pinCode,
        amount = amount
    )
}
