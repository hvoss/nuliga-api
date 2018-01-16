package de.hvoss.nuligaapi.dataaccess

import java.nio.file.Files
import java.nio.file.Paths

class NuLigaTestDAO : NuLigaDAO {
    override fun loadRegionMeetingsFOP(championship : String) : String {
        return String(
                Files.readAllBytes(
                        Paths.get(javaClass.classLoader.getResource("$championship.csv").toURI())
                ),
                charset("ISO-8859-1")
        );

    }
}