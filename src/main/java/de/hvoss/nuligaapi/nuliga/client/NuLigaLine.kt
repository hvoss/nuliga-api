package de.hvoss.nuligaapi.nuliga.client

import java.time.LocalDateTime

data class NuLigaLine (
        val date: LocalDateTime,
        val hallId: Int?,
        val matchNo: Int?,
        val hallTooltip: String,
        val league: String,
        val seasonName: String,
        val homeTeamName: String,
        val guestTeamName: String,
        val gameScore: NuLigaLineScore?,
        val halftimeScore: NuLigaLineScore?,
        val firstReferee: NuLigaLineReferee?,
        val secondReferee: NuLigaLineReferee?,
        val thirdReferee: NuLigaLineReferee?,
        val fourthReferee: NuLigaLineReferee?
)

data class NuLigaLineScore (
        val homeGoals : Int,
        val guestGoals : Int
)

data class NuLigaLineReferee (
        val name : String,
        val clubName : String
)