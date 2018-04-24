package de.hvoss.nuligaapi.nuliga.client

import org.apache.http.client.config.RequestConfig
import org.springframework.stereotype.Component
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import org.slf4j.LoggerFactory
import java.util.*
import java.util.regex.Pattern
import java.util.stream.Collectors
import java.util.stream.Stream


@Component
class NuLigaDAOImpl : NuLigaDAO {

    private val log = LoggerFactory.getLogger(NuLigaDAOImpl::class.java)

    private val BASE_URL = "https://bremerhv-handball.liga.nu"

    private val PATH_CSV = "cgi-bin/WebObjects/nuLigaDokumentHBDE.woa/wa/nuDokument?dokument=RegionMeetingsFOP&championship="

    private val PATH_CLUB_SEARCH = "cgi-bin/WebObjects/nuLigaHBDE.woa/wa/clubSearch?federation=HVN"

    private val timeoutInSeconds = 30;
    private val httpConfig = RequestConfig.custom()
            .setConnectTimeout(timeoutInSeconds * 1000)
            .setConnectionRequestTimeout(timeoutInSeconds * 1000)
            .setSocketTimeout(timeoutInSeconds * 1000).build()

    private val httpClient = HttpClientBuilder.create().setDefaultRequestConfig(httpConfig).build()

    private val matcherRegion = Pattern.compile("(searchPattern=.*)&amp;federations=HVN")

    private val matcherClub = Pattern.compile("<tr>\\s*?<td>.*?<\\/tr>", Pattern.DOTALL)

    override fun loadCSV(championship: String): String {
        val httpGet = HttpGet("$BASE_URL/$PATH_CSV$championship")

        val httpResponse = httpClient.execute(httpGet)

        return EntityUtils.toString(httpResponse.entity)
    }

    override fun loadClubSearch(): Stream<String> {
        return Scanner(retrieveSite("$BASE_URL/$PATH_CLUB_SEARCH"))
                     .findAll(matcherRegion)
                     .parallel()
                     .map { m -> m.group(1) }
                     .map { p -> retrieveSite("$BASE_URL/$PATH_CLUB_SEARCH&$p")}
                     .flatMap(this::parseClubSite)
    }


    private fun parseClubSite(data : String) : Stream<String> {
        return Scanner(data).findAll(matcherClub).map { m -> m.group() }
    }

    private fun retrieveSite(url : String) : String {
        val httpGet = HttpGet(url)

        val httpResponse = httpClient.execute(httpGet)

        return EntityUtils.toString(httpResponse.entity)
    }
}

