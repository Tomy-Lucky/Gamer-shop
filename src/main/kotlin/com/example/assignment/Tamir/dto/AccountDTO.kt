package com.example.assignment.Tamir.dto

import java.math.BigDecimal

data class AccountDTO(
    val id: Long,
    val userName: String,
    val cardNumber: String,
    val pinCode: String,
    val balance: BigDecimal
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AccountDTO

        if (id != other.id) return false
        if (userName != other.userName) return false
        if (cardNumber != other.cardNumber) return false
        if (pinCode != other.pinCode) return false
        if (balance != other.balance) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + userName.hashCode()
        result = 31 * result + cardNumber.hashCode()
        result = 31 * result + pinCode.hashCode()
        result = 31 * result + balance.hashCode()
        return result
    }
}

data class AccountUpdateOptions(
    val userName: String? = null,
    val cardNumber: String? = null,
    val pinCode: String? = null,
    val balance: BigDecimal? = null
)
