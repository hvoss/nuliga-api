package de.hvoss.nuligaapi.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable
import java.time.LocalDateTime

@RedisHash("Club")
data class Club (
        @Id
        val name: String
) : Serializable

@RedisHash("Referee")
data class Referee
(
        @Id
        val name : String,
        val club : Club
) : Serializable

@RedisHash("Team")
data class Team (
        val club : Club,
        val name : String
) : Serializable

@RedisHash("Score")
data class Score (
        val homeGoals : Int,
        val guestGoals : Int
) : Serializable

@RedisHash("League")
data class League (
        val name : String
) : Serializable

@RedisHash("Season")
data class Season (
        val name : String
) : Serializable

@RedisHash("Match")
data class Match (
        val no : Int,
        val date : LocalDateTime,
        val homeTeam : Team,
        val guestTeam : Team,
        val gameScore: Score?,
        val halftimeScore: Score?,
        val firstReferee : Referee?,
        val secondReferee : Referee?
) : Serializable
