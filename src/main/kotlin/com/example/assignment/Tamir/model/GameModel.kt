package com.example.assignment.Tamir.model

import com.example.assignment.Tamir.dto.Game
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "game")
class GameModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long,

    @Column(unique = true)
    var name: String,

    @Column
    var price: BigDecimal,

    @ManyToMany
    var accounts: List<AccountModel>
) {

    fun toDTO() = Game(
        id = id,
        name = name,
        price = price
    )
}
