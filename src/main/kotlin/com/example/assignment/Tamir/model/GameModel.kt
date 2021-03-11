package com.example.assignment.Tamir.model

import com.example.assignment.Tamir.dto.GameDTO
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "game")
class GameModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long,

    @Column(unique = true)
    var gameName: String,

    @Column
    var price: BigDecimal
) {

    fun toDTO() = GameDTO(
        id = id,
        gameName = gameName,
        price = price
    )
}
