package de.hvoss.nuligaapi.nuliga.client

import org.apache.commons.text.StringEscapeUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.util.EntityUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*
import java.util.regex.MatchResult
import java.util.regex.Pattern
import java.util.stream.Stream

const val BASE_URL = "https://bremerhv-handball.liga.nu"

const val PATH_CSV = "cgi-bin/WebObjects/nuLigaDokumentHBDE.woa/wa/nuDokument?dokument=RegionMeetingsFOP&championship="

const val PATH_CLUB_SEARCH = "cgi-bin/WebObjects/nuLigaHBDE.woa/wa/clubSearch?federation=HVN"

val matcherRegion = Pattern.compile("(searchPattern=.*)&amp;federations=HVN")!!

val matcherClub = Pattern.compile("<tr>\\s*?<td>\\s*.*?clubInfoDisplay\\?club=(\\d+)\">(.+?)<.*?\\((\\d+).*?</tr>", Pattern.DOTALL)!!

private val log = LoggerFactory.getLogger(NuLigaDAOImpl::class.java)

@Component
class NuLigaDAOImpl(private val httpClient : CloseableHttpClient) : NuLigaDAO {

    override fun loadCSV(championship: String): String {
        val httpGet = HttpGet("$BASE_URL/$PATH_CSV$championship")

        val httpResponse = httpClient.execute(httpGet)

        return EntityUtils.toString(httpResponse.entity)
    }

    override fun loadClubSearch(): Stream<NuLigaDAO.Club> {
        return Scanner(retrieveSite("$BASE_URL/$PATH_CLUB_SEARCH"))
                     .findAll(matcherRegion)
                     .parallel()
                     .map { m -> m.group(1) }
                     .map { p -> retrieveSite("$BASE_URL/$PATH_CLUB_SEARCH&$p")}
                     .flatMap(this::parseClubSite)
    }


    private fun parseClubSite(data : String) : Stream<NuLigaDAO.Club> {
        return Scanner(data).findAll(matcherClub)
                            .map(this::toClub)

    }

    private fun toClub(m : MatchResult) : NuLigaDAO.Club {
        val internalId = m.group(1).toInt()
        val name = StringEscapeUtils.unescapeHtml4(m.group(2))
        val publicId = m.group(3).toInt()
        return NuLigaDAO.Club(publicId, internalId, name)
    }

    private fun retrieveSite(url : String) : String {
        log.debug("load url: $url")
        val httpGet = HttpGet(url)

        val httpResponse = httpClient.execute(httpGet)

        httpResponse.use {
            return EntityUtils.toString(httpResponse.entity)
        }
    }
}



