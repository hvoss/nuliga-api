package de.hvoss.nuligaapi.controller;

import de.hvoss.nuligaapi.dataaccess.ClubRepository
import de.hvoss.nuligaapi.dataaccess.RefereeRepository
import de.hvoss.nuligaapi.nuliga.client.NuLigaAccess
import de.hvoss.nuligaapi.model.Club
import de.hvoss.nuligaapi.model.Referee
import de.hvoss.nuligaapi.nuliga.client.NuLigaLineReferee
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*
import org.springframework.scheduling.annotation.Scheduled
import java.sql.Ref
import java.util.stream.Collector
import java.util.stream.Collectors


@Component
class ModelController(private val nuLigaAccess: NuLigaAccess, private val clubRepository: ClubRepository, private val refereeRepository: RefereeRepository) {

    private val log = LoggerFactory.getLogger(ModelController::class.java)

    @Scheduled(cron = "0 * * * * *")
    fun updateData() {
        log.info("before load")
        val matches = nuLigaAccess.readRegionMeetingsFOP("HVN+2016%2F17")
        log.info("after load")

        log.info("before filter")
        val clubs = matches.stream().flatMap { l -> Arrays.asList(l.firstReferee, l.secondReferee, l.thirdReferee, l.fourthReferee).stream() }
                .filter { r -> r != null }
                .filter {c -> c!!.clubName != null && c!!.clubName.isNotEmpty() }
                .map { r -> Club(name = r!!.clubName) }
                .distinct()
                .collect(Collectors.toList())
        log.info("after filter")

        log.info("before save")
        clubRepository.save(clubs)
        log.info("after save")


        val referees = matches.stream().flatMap { l -> Arrays.asList(l.firstReferee, l.secondReferee, l.thirdReferee, l.fourthReferee).stream() }
                .distinct()
                .map { r -> toReferee(r) }
                .collect(Collectors.toList())
                .filterNotNull()


        refereeRepository.save(referees)
    }

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


