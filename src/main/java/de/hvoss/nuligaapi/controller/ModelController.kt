package de.hvoss.nuligaapi.controller;

import de.hvoss.nuligaapi.dataaccess.NuLigaAccess
import de.hvoss.nuligaapi.model.Club
import de.hvoss.nuligaapi.model.Referee
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

class ModelController(nuLigaAccess : NuLigaAccess) {

    val nuLigaAccess = nuLigaAccess


    fun buildModel() {
        val matches = nuLigaAccess.readRegionMeetingsFOP("HVN+2016%2F17")

        val clubs = matches.stream().flatMap { l -> Arrays.asList(l.firstReferee, l.secondReferee, l.thirdReferee, l.fourthReferee).stream() }
                .filter { r -> r != null }
                .map { r -> Club(name = r!!.clubName) }
                .collect(Collectors.toSet())

        System.out.print(clubs)
    }

}
