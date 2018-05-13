package de.hvoss.nuligaapi.nuliga.client

import de.hvoss.test.http.mockHttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.stream.Collectors


class NuLigaDAOImplTest {

    private lateinit var sut : NuLigaDAOImpl

    @Mock
    private lateinit var httpClient : CloseableHttpClient

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this);
        sut = NuLigaDAOImpl(httpClient)
    }

    @Test
    fun testLoadClubSearch() {
        httpClient.mockHttpGet("https://bremerhv-handball.liga.nu/cgi-bin/WebObjects/nuLigaHBDE.woa/wa/clubSearch?federation=HVN", "clubSearch.html")
        httpClient.mockHttpGet("https://bremerhv-handball.liga.nu/cgi-bin/WebObjects/nuLigaHBDE.woa/wa/clubSearch?federation=HVN&searchPattern=DE.NO.01.01&amp;federation=HVN&amp;regionName=HR+S%C3%BCd-Ost-Niedersachsen", "clubSearchSON.html")
        httpClient.mockHttpGet("https://bremerhv-handball.liga.nu/cgi-bin/WebObjects/nuLigaHBDE.woa/wa/clubSearch?federation=HVN&searchPattern=DE.NO.01.19&amp;federation=HVN&amp;regionName=Bremer+HV", "clubSearchBremen.html")

        val list = sut.loadClubSearch().collect(Collectors.toList())

        assertThat(list.size, equalTo(95));

        val clubSGBN = list.find { c -> c.publicId == 913 }!!
        assertThat(clubSGBN.internalId, equalTo(48494))
        assertThat(clubSGBN.publicId, equalTo(913))
        assertThat(clubSGBN.name, equalTo("SG Buntentor/Neustadt"))
    }



    
}