package de.hvoss.nuligaapi.nuliga.client

import de.hvoss.nuligaapi.model.Club
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern
import java.util.stream.Collectors
import java.util.stream.Stream

@Component
class NuLigaAccessImpl(private val nuLigaDAO: NuLigaDAO) : NuLigaAccess {


    val DF = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

    val SCORE_PATTERN = Pattern.compile("^=\"(\\d+):(\\d+)\"$")

    override fun readRegionMeetingsFOP(championship: String): List<NuLigaLine> {
        val data = nuLigaDAO.loadCSV(championship)

        return data.split("\n")
                   .stream()
                   .skip(1)
                   .filter { line -> line.isNotBlank()}
                   .map { line -> convert(line) }
                   .collect(Collectors.toList())
    }

    @Suppress("UNUSED_CHANGED_VALUE")
    private fun convert(line: String): NuLigaLine {
        val cells = line.split(";")

        var cellNo = 0

        cellNo++ // Weekday, ignored

        val date = LocalDateTime.parse(cells[cellNo++] + " " + cells[cellNo++], DF);

        return NuLigaLine(
                date = date,
                hallId = convertInt(cells[cellNo++]),
                hallTooltip = cells[cellNo++],
                matchNo = convertInt(cells[cellNo++]),
                league = cells[cellNo++],
                seasonName = cells[cellNo++],
                homeTeamName = cells[cellNo++],
                guestTeamName = cells[cellNo++],
                gameScore = convertScore(cells[cellNo++]),
                halftimeScore = convertScore(cells[cellNo++]),
                firstReferee = convertReferee(cells[cellNo++], cells[cellNo++]),
                secondReferee = convertReferee(cells[cellNo++], cells[cellNo++]),
                thirdReferee = convertReferee(cells[cellNo++], cells[cellNo++]),
                fourthReferee = convertReferee(cells[cellNo++], cells[cellNo++])
        )

    }

    private fun convertScore (str : String) : NuLigaLineScore? {
        val s = SCORE_PATTERN.matcher(str)
        if (s.find()) {
            return NuLigaLineScore(s.group(1).toInt(), s.group(2).toInt())
        }
        return null
    }

    private fun convertInt(str : String) : Int? {
        if (str.isBlank()) {
            return null
        }
        return str.toInt()
    }

    private fun convertReferee(name : String, clubName : String) : NuLigaLineReferee? {
        if (name.isNotBlank() || clubName.isNotBlank()) {
            return NuLigaLineReferee(name.trim(), clubName.trim())
        }
        return null
    }

    override fun readClubs() : Stream<Club> {
        return nuLigaDAO.loadClubSearch().map(this::toClub);
    }

    private fun toClub(line : NuLigaDAO.Club) : Club {
        return Club(line.internalId, line.publicId, line.name)
    }

}