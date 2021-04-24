package com.example.assignment.Tamir.dto

import java.math.BigDecimal

data class Game(
    val id: Long,
    val name: String,
    val price: BigDecimal
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Game

        if (id != other.id) return false
        if (name != other.name) return false
        if (price != other.price) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + price.hashCode()
        return result
    }
}

data class GameUpdateOptions(
    val gameName: String? = null,
    val price: BigDecimal? = null
)

data class PurchasedGame(
    val gameName: String,
    val price: BigDecimal,
    val balance: BigDecimal,
    val message: String
)
