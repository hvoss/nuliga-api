package de.hvoss.nuligaapi.controller;

import de.hvoss.nuligaapi.dataaccess.ClubRepository
import de.hvoss.nuligaapi.nuliga.client.NuLigaAccess
import de.hvoss.nuligaapi.model.Club
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*
import org.springframework.scheduling.annotation.Scheduled
import java.util.stream.Collectors


@Component
class ModelController(private val nuLigaAccess: NuLigaAccess, private val clubRepository: ClubRepository) {

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
        clubRepository.saveAll(clubs)
        log.info("after save")
    }

}
