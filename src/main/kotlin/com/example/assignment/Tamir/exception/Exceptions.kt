package com.example.assignment.Tamir.exception

import java.math.BigDecimal

// Account
class AccountNotFoundByUserNameAndPassword(userName: String, password: String) :
    RuntimeException("Account not found by userName: $userName, password: $password")

class CardNumberAlreadyExistsException(cardNumber: String) :
    RuntimeException("Card number $cardNumber is already exists")

class AccountNotFoundByNumberException(cardNumber: String) : RuntimeException("No such $cardNumber card number")
class AccountNotFoundByIDException(id: Long) : RuntimeException("No such account by id $id")
class IncorrectPinCodeException : RuntimeException("Incorrect pin code")
class IncorrectWithdrawAmount(amount: BigDecimal) : RuntimeException("Please choose less withdraw amount than $amount")
class NegativeAmountException(amount: BigDecimal) : RuntimeException("Negative amount of cash or zero - $amount")

// Game
class GameNotFoundByIdException(id: Long) : RuntimeException("No such game by id $id")
class GameNotFoundByGameNameException(gameName: String) : RuntimeException("No such game by name $gameName")
class GameAlreadyExistsByNameException(gameName: String) : RuntimeException("Game already exists by name $gameName")
