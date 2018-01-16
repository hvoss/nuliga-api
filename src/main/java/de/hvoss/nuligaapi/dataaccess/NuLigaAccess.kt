package de.hvoss.nuligaapi.dataaccess;

interface NuLigaAccess {

    fun readRegionMeetingsFOP(championship : String) : List<NuLigaLine>
}
