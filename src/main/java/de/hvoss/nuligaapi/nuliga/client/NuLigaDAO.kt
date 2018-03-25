package de.hvoss.nuligaapi.nuliga.client

import java.io.File
import org.springframework.core.io.ClassPathResource
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


interface NuLigaDAO {

    // https://bremerhv-handball.liga.nu/cgi-bin/WebObjects/nuLigaDokumentHBDE.woa/wa/nuDokument?dokument=RegionMeetingsFOP&championship=HVN+2016%2F17


    fun loadRegionMeetingsFOP(championship : String) : String
}