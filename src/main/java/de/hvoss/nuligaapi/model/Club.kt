package de.hvoss.nuligaapi.model

import java.time.LocalDateTime

data class Club(
        val name: String
)

data class Referee (
        val name : String,
        val club : Club
)

data class Team (
        val club : Club,
        val name : String
)

data class Score (
        val homeGoals : Int,
        val guestGoals : Int
)

data class League (
        val name : String
)

data class Season (
        val name : String
)

data class Match (
        val no : Int,
        val date : LocalDateTime,
        val homeTeam : Team,
        val guestTeam : Team,
        val gameScore: Score?,
        val halftimeScore: Score?,
        val firstReferee : Referee?,
        val secondReferee : Referee?
)
