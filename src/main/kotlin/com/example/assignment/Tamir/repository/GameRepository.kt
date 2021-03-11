package com.example.assignment.Tamir.repository

import com.example.assignment.Tamir.model.GameModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GameRepository : JpaRepository<GameModel, Long> {

    fun findByGameName(gameName: String): GameModel?
}
