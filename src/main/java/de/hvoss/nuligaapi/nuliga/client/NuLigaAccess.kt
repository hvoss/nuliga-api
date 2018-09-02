package de.hvoss.nuligaapi.nuliga.client;

import de.hvoss.nuligaapi.model.Club

interface NuLigaAccess {

    fun readRegionMeetingsFOP(championship : String) : List<NuLigaLine>

    fun readClubs(): List<Club>
}
