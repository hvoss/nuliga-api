package de.hvoss.nuligaapi.nuliga.client;

interface NuLigaAccess {

    fun readRegionMeetingsFOP(championship : String) : List<NuLigaLine>
}
