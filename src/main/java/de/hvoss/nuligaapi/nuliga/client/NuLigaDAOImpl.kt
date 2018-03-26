package de.hvoss.nuligaapi.nuliga.client

import org.apache.http.client.ResponseHandler
import org.apache.http.client.config.RequestConfig
import org.springframework.stereotype.Component
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import java.io.StringBufferInputStream
import java.io.StringReader


@Component
class NuLigaDAOImpl : NuLigaDAO {
    private val BASE_URL = "https://bremerhv-handball.liga.nu/cgi-bin/WebObjects/nuLigaDokumentHBDE.woa/wa/nuDokument?dokument=RegionMeetingsFOP&championship="

    override fun loadRegionMeetingsFOP(championship: String): String {
        val timeout = 30;
        val config = RequestConfig.custom()
                .setConnectTimeout(timeout * 1000)
                .setConnectionRequestTimeout(timeout * 1000)
                .setSocketTimeout(timeout * 1000).build()

        val httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

        val httpget = HttpGet(
                "$BASE_URL$championship")

        val httpResponse = httpclient.execute(httpget)

        return EntityUtils.toString(httpResponse.entity)
    }

}