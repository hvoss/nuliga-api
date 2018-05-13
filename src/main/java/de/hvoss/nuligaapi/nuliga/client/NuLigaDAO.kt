package de.hvoss.nuligaapi.nuliga.client

import java.util.stream.Stream


interface NuLigaDAO {

    // https://bremerhv-handball.liga.nu/cgi-bin/WebObjects/nuLigaDokumentHBDE.woa/wa/nuDokument?dokument=RegionMeetingsFOP&championship=HVN+2016%2F17


    fun loadCSV(championship : String) : String

    fun loadClubSearch() : Stream<Club>

    data class Club (
         var publicId : Int,
         var internalId : Int,
         var name : String
         )
}