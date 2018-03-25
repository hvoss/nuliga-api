package de.hvoss.nuligaapi.controller;

import de.hvoss.nuligaapi.dataaccess.ClubRepository
import de.hvoss.nuligaapi.nuliga.client.NuLigaAccess
import de.hvoss.nuligaapi.model.Club
import de.hvoss.nuligaapi.service.ClubController
import java.util.*
import java.util.stream.Collectors
import java.net.URI
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig





class ModelController(nuLigaAccess : NuLigaAccess, clubRepository: ClubRepository) {

    val nuLigaAccess = nuLigaAccess

    val clubRepository = clubRepository

    fun buildModel() {
        val matches = nuLigaAccess.readRegionMeetingsFOP("HVN+2016%2F17")

        val clubs = matches.stream().flatMap { l -> Arrays.asList(l.firstReferee, l.secondReferee, l.thirdReferee, l.fourthReferee).stream() }
                .filter { r -> r != null }
                .map { r -> Club(name = r!!.clubName) }
                .forEach{c -> clubRepository.save(c)}



        System.out.print(clubs)
    }

}
