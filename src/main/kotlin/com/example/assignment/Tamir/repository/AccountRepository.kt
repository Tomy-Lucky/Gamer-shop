package com.example.assignment.Tamir.repository

import com.example.assignment.Tamir.model.AccountModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository: JpaRepository<AccountModel, Long> {

    fun findAccountByCardNumber(cardNumber: String): AccountModel?

    fun findAccountModelByUserNameAndPassword(userName: String, password: String): AccountModel?
}
