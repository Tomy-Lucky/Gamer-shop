package com.example.assignment.Tamir.service

import com.example.assignment.Tamir.dto.Account
import com.example.assignment.Tamir.dto.AccountUpdateOptions
import com.example.assignment.Tamir.exception.*
import com.example.assignment.Tamir.model.AccountModel
import com.example.assignment.Tamir.model.toDTO
import com.example.assignment.Tamir.repository.AccountRepository
import com.example.assignment.Tamir.repository.GameRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val gameRepository: GameRepository
) {

    @Transactional
    fun findAll() = accountRepository.findAll().map { it.toDTO() }

    @Transactional
    fun findByNameAndPassword(userName: String, password: String) =
        accountRepository.findAccountModelByUserNameAndPassword(
            userName = userName,
            password = password
        )?.toDTO() ?: throw AccountNotFoundByUserNameAndPassword(userName = userName, password = password)

    @Transactional
    fun createAccount(account: Account): Account {
        val accountModel = accountRepository.findAccountByCardNumber(cardNumber = account.cardNumber)
        if (accountModel != null) throw CardNumberAlreadyExistsException(accountModel.cardNumber)

        return accountRepository.save(
            AccountModel(
                id = 0,
                userName = account.userName,
                password = account.password,
                cardNumber = account.cardNumber,
                pinCode = account.pinCode,
                balance = account.balance,
                games = mutableListOf()
            )
        ).toDTO()
    }

    private fun getAccountByCardNumber(cardNumber: String) = (accountRepository.findAccountByCardNumber(cardNumber)
        ?: throw AccountNotFoundByNumberException(cardNumber)).toDTO()

    @Transactional
    fun getAccountByPinCode(cardNumber: String, pinCode: String): Account {
        val account = getAccountByCardNumber(cardNumber)

        if (account.pinCode != pinCode)
            throw IncorrectPinCodeException()

        return account
    }

    @Transactional
    fun changePinCode(cardNumber: String, pinCode: String, newPinCode: String): Account {
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
    fun withdraw(cardNumber: String, pinCode: String, amount: BigDecimal): Account {
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
    fun topUp(cardNumber: String, pinCode: String, amount: BigDecimal): Account {
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
    fun update(id: Long, updateOptions: AccountUpdateOptions): Account {
        val accountModel = accountRepository.findByIdOrNull(id) ?: throw AccountNotFoundByIDException(id)

        if (updateOptions.userName != null) accountModel.userName = updateOptions.userName
        if (updateOptions.cardNumber != null) accountModel.cardNumber = updateOptions.cardNumber
        if (updateOptions.pinCode != null) accountModel.pinCode = updateOptions.pinCode
        if (updateOptions.balance != null) accountModel.balance = updateOptions.balance

        return accountModel.toDTO()
    }

    @Transactional
    fun addGameToAccount(accountId: Long, gameId: Long): Account {
        val accountModel =
            accountRepository.findByIdOrNull(accountId) ?: throw AccountNotFoundByIDException(accountId)
        val gameModel = gameRepository.findByIdOrNull(gameId) ?: throw GameNotFoundByIdException(gameId)
        accountModel.games.add(gameModel)

        return accountModel.toDTO()
    }
}
