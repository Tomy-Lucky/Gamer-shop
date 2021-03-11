package com.example.assignment.Tamir.service

import com.example.assignment.Tamir.dto.AccountDTO
import com.example.assignment.Tamir.dto.AccountUpdateOptions
import com.example.assignment.Tamir.exception.*
import com.example.assignment.Tamir.model.AccountModel
import com.example.assignment.Tamir.model.toDTO
import com.example.assignment.Tamir.repository.AccountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class AccountService(
    private val accountRepository: AccountRepository
) {

    fun findAll() = accountRepository.findAll().map {
        it.toDTO()
    }

    @Transactional
    fun createAccount(accountDTO: AccountDTO): AccountDTO {
        val account = accountRepository.findAccountByCardNumber(cardNumber = accountDTO.cardNumber)
        if (account != null) throw CardNumberAlreadyExistsException(accountDTO.cardNumber)

        return accountRepository.save(
            AccountModel(
                id = 0,
                userName = accountDTO.userName,
                cardNumber = accountDTO.cardNumber,
                pinCode = accountDTO.pinCode,
                balance = accountDTO.balance
            )
        ).toDTO()
    }

    private fun getAccountByCardNumber(cardNumber: String) = (accountRepository.findAccountByCardNumber(cardNumber)
        ?: throw CardNumberNotFoundByNumberException(cardNumber)).toDTO()

    @Transactional
    fun getAccountByPinCode(cardNumber: String, pinCode: String): AccountDTO {
        val account = getAccountByCardNumber(cardNumber)

        if (account.pinCode != pinCode)
            throw IncorrectPinCodeException()

        return account
    }

    @Transactional
    fun changePinCode(cardNumber: String, pinCode: String, newPinCode: String): AccountDTO {
        val account = getAccountByPinCode(
            cardNumber = cardNumber,
            pinCode = pinCode
        )

        return update(
            id = account.id,
            updateOptions = AccountUpdateOptions(
                pinCode = newPinCode
            )
        )
    }

    @Transactional
    fun withdraw(cardNumber: String, pinCode: String, amount: BigDecimal): AccountDTO {
        if (amount <= BigDecimal("0")) throw NegativeAmountException(amount)

        val account = getAccountByPinCode(
            cardNumber = cardNumber,
            pinCode = pinCode
        )

        if (amount > account.balance) throw IncorrectWithdrawAmount(amount)

        return update(
            id = account.id,
            updateOptions = AccountUpdateOptions(
                balance = account.balance - amount
            )
        )
    }

    @Transactional
    fun topUp(cardNumber: String, pinCode: String, amount: BigDecimal): AccountDTO {
        if (amount <= BigDecimal("0")) throw NegativeAmountException(amount)

        val account = getAccountByPinCode(
            cardNumber = cardNumber,
            pinCode = pinCode
        )

        return update(
            id = account.id,
            updateOptions = AccountUpdateOptions(
                balance = account.balance + amount
            )
        )
    }

    @Transactional
    fun update(id: Long, updateOptions: AccountUpdateOptions): AccountDTO {
        val accountModel = accountRepository.findByIdOrNull(id) ?: throw CardNumberNotFoundByIDException(id)

        if (updateOptions.userName != null) accountModel.userName = updateOptions.userName
        if (updateOptions.cardNumber != null) accountModel.cardNumber = updateOptions.cardNumber
        if (updateOptions.pinCode != null) accountModel.pinCode = updateOptions.pinCode
        if (updateOptions.balance != null) accountModel.balance = updateOptions.balance

        return accountModel.toDTO()
    }

}
