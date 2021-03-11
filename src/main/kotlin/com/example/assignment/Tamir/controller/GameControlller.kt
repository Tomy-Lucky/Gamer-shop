package com.example.assignment.Tamir.controller

import com.example.assignment.Tamir.dto.GameDTO
import com.example.assignment.Tamir.dto.GameUpdateOptions
import com.example.assignment.Tamir.service.GameService
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/game")
class GameControlller(
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
    fun addGame(@RequestBody gameDTO: GameDTO) = gameService.addGame(gameDTO)

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
