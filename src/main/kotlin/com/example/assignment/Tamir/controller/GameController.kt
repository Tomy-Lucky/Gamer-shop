package com.example.assignment.Tamir.controller

import com.example.assignment.Tamir.dto.Game
import com.example.assignment.Tamir.dto.GameUpdateOptions
import com.example.assignment.Tamir.service.GameService
import java.math.BigDecimal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/game")
class GameController(
    private val gameService: GameService
) {

    @GetMapping("/find-all")
    fun findAll() = gameService.findAll()

    @GetMapping("/find-by-name")
    fun getGameByName(@RequestParam("gameName", required = true) gameName: String) =
        gameService.findByGameName(gameName)

    @GetMapping("/find-by-id")
    fun getGameById(@RequestParam("id", required = true) id: Long) = gameService.findById(id)


    @PostMapping("/add")
    fun addGame(@RequestBody game: Game) = gameService.addGame(game)

    @GetMapping("/modify-by-id")
    fun modifyGameById(
        @RequestParam("id", required = true) id: Long,
        @RequestParam("gameName", required = false) gameName: String? = null,
        @RequestParam("price", required = false) price: BigDecimal? = null,
    ) = gameService.modifyGame(
        id = id,
        updateOptions = GameUpdateOptions(
            gameName = gameName,
            price = price
        )
    )

    @GetMapping("/buy-game-by-id")
    fun buyGameById(
        @RequestParam("id", required = true) id: Long,
        @RequestParam("cardNumber", required = true) cardNumber: String,
        @RequestParam("pinCode", required = true) pinCode: String,
    ) = gameService.buyGame(
        gameId = id,
        cardNumber = cardNumber,
        pinCode = pinCode
    )
}
