package de.hvoss.nuligaapi.nuliga.client

import org.junit.Test

import org.junit.Assert.*

class NuLigaAccessTest {

    @Test
    fun readNuLiga() {
        val dao = NuLigaTestDAO()
        val access = NuLigaAccessImpl(dao)
        val readRegionMeetingsFOP = access.readRegionMeetingsFOP("HVN+2016%2F17")
        System.out.print(readRegionMeetingsFOP)
    }

    @Test
    fun readClubs() {
        val dao = NuLigaDAOImpl()
        val access = NuLigaAccessImpl(dao)
        val clubs = access.readClubs()
        System.out.print(clubs)
    }


}