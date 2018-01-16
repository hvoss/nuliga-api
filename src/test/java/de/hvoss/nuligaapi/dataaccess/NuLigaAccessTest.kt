package de.hvoss.nuligaapi.dataaccess

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
}