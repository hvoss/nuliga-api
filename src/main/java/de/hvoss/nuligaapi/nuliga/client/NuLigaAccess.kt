package de.hvoss.nuligaapi.nuliga.client;

import de.hvoss.nuligaapi.model.Club
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

interface NuLigaAccess {

    fun readRegionMeetingsFOP(championship : String) : List<NuLigaLine>

    fun readClubs(): List<Club>
}
