package com.example.assignment.Tamir.model

import com.example.assignment.Tamir.dto.AccountDTO
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "account")
class AccountModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long,

    @Column(unique = true)
    var userName: String,

    @Column
    var cardNumber: String,

    @Column
    var pinCode: String,

    @Column
    var balance: BigDecimal
)

fun AccountModel.toDTO() = AccountDTO(
    id = id,
    userName = userName,
    cardNumber = cardNumber,
    pinCode = pinCode,
    balance = balance
)
