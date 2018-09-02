package de.hvoss.nuligaapi.nuliga.client;

import de.hvoss.nuligaapi.model.Club
import java.util.stream.Stream

interface NuLigaAccess {

    fun readRegionMeetingsFOP(championship : String) : List<NuLigaLine>

    fun readClubs(): Stream<Club>
}
