package de.hvoss.nuligaapi.controller;

import de.hvoss.nuligaapi.dataaccess.ClubRepository
import de.hvoss.nuligaapi.dataaccess.RefereeRepository
import de.hvoss.nuligaapi.nuliga.client.NuLigaAccess
import de.hvoss.nuligaapi.model.Club
import de.hvoss.nuligaapi.model.Referee
import de.hvoss.nuligaapi.model.Team
import de.hvoss.nuligaapi.nuliga.client.NuLigaLine
import de.hvoss.nuligaapi.nuliga.client.NuLigaLineReferee
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.Ref
import java.util.stream.Collector
import java.util.stream.Collectors


@RestController
@Component
class ModelController(private val nuLigaAccess: NuLigaAccess, private val clubRepository: ClubRepository, private val refereeRepository: RefereeRepository) {

    private val log = LoggerFactory.getLogger(ModelController::class.java)

    @GetMapping("update")
    @Scheduled(cron = "0 * * * * *")
    fun updateData() {
        //val matches = nuLigaAccess.readRegionMeetingsFOP("HVN+2016%2F17")

        val clubs = nuLigaAccess.readClubs();

        clubRepository.save(clubs);

//        clubRepository.save(extractClubs(matches))
//        refereeRepository.save(extractReferees(matches))

    }
/*
    private fun extractReferees(lines: List<NuLigaLine>): List<Referee> {
        return lines.stream().flatMap { l -> listOfNotNull(l.firstReferee, l.secondReferee, l.thirdReferee, l.fourthReferee).stream() }
                .distinct()
                .map { r -> toReferee(r) }
                .collect(Collectors.toList())
                .filterNotNull()
    }

    private fun extractClubs(lines: List<NuLigaLine>): List<Club> {
        return lines.stream().flatMap { l -> listOfNotNull(l.firstReferee?.clubName, l.secondReferee?.clubName, l.thirdReferee?.clubName, l.fourthReferee?.clubName).stream() }
                .distinct()
                .map { name -> Club(name = name) }
                .collect(Collectors.toList())
                .filterNotNull()
    }

    private fun extractTeams(lines: List<NuLigaLine>): List<Team> {
        return lines.stream().flatMap { l -> listOfNotNull(l.homeTeamName, l.guestTeamName).stream() }
                .distinct()
                .map { teamName -> Team(Club("a"), teamName) }
                .collect(Collectors.toList())
                .filterNotNull()

    }
*/
    private fun toReferee(nuReferee :  NuLigaLineReferee?) : Referee? {
        if (nuReferee != null) {
            val club = clubRepository.findOne(nuReferee.clubName)
            if (club != null) {
                return Referee(name = nuReferee.name, club = club)
            }
        }
        return null
    }
}


