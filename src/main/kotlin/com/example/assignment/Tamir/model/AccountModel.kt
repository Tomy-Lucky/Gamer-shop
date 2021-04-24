package com.example.assignment.Tamir.model

import com.example.assignment.Tamir.dto.Account
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "account")
class AccountModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long,

    @Column(unique = true)
    var userName: String,

    @Column(unique = true)
    var password: String,

    @Column
    var cardNumber: String,

    @Column
    var pinCode: String,

    @Column
    var balance: BigDecimal,

    @ManyToMany
    var games: MutableList<GameModel>
)

fun AccountModel.toDTO() = Account(
    id = id,
    userName = userName,
    password = password,
    cardNumber = cardNumber,
    pinCode = pinCode,
    balance = balance,
    games = games.map { it.toDTO() }
)
