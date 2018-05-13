package de.hvoss.nuligaapi.nuliga.client

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Stream

class NuLigaTestDAO : NuLigaDAO {
    override fun loadClubSearch(): Stream<NuLigaDAO.Club> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadCSV(championship : String) : String {
        return String(
                Files.readAllBytes(
                        Paths.get(javaClass.classLoader.getResource("$championship.csv").toURI())
                ),
                charset("ISO-8859-1")
        );

    }
}