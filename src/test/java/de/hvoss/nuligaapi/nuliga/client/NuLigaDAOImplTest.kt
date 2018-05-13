package de.hvoss.nuligaapi.nuliga.client

import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.CloseableHttpClient
import org.hamcrest.BaseMatcher
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Description
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.slf4j.LoggerFactory
import java.net.URI
import java.nio.file.Files
import java.nio.file.Paths
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
        mockHttpGet("https://bremerhv-handball.liga.nu/cgi-bin/WebObjects/nuLigaHBDE.woa/wa/clubSearch?federation=HVN", "clubSearch.html")
        mockHttpGet("https://bremerhv-handball.liga.nu/cgi-bin/WebObjects/nuLigaHBDE.woa/wa/clubSearch?federation=HVN&searchPattern=DE.NO.01.01&amp;federation=HVN&amp;regionName=HR+S%C3%BCd-Ost-Niedersachsen", "clubSearchSON.html")
        mockHttpGet("https://bremerhv-handball.liga.nu/cgi-bin/WebObjects/nuLigaHBDE.woa/wa/clubSearch?federation=HVN&searchPattern=DE.NO.01.19&amp;federation=HVN&amp;regionName=Bremer+HV", "clubSearchBremen.html")

        val list = sut.loadClubSearch().collect(Collectors.toList())

        assertThat(list.size, equalTo(95));

        val clubSGBN = list.find { c -> c.publicId == 913 }!!
        assertThat(clubSGBN.internalId, equalTo(48494))
        assertThat(clubSGBN.publicId, equalTo(913))
        assertThat(clubSGBN.name, equalTo("SG Buntentor/Neustadt"))
    }

    private fun mockHttpGet(url : String, localeFile : String) {
        val file = String(
                Files.readAllBytes(
                        Paths.get(javaClass.classLoader.getResource(localeFile).toURI())
                ),
                charset("UTF-8")
        );

        val entity = StringEntity(file)
        val response = Mockito.mock(CloseableHttpResponse::class.java)

        val argument = ArgumentCaptor.forClass(HttpUriRequest::class.java)
        
        given(httpClient.execute(Mockito.argThat(UriMatcher(URI(url))))).willReturn(response)
        given(response.entity).willReturn(entity)

    }

    class UriMatcher(private val uri : URI) : BaseMatcher<HttpUriRequest>() {
        private val log = LoggerFactory.getLogger(UriMatcher::class.java)
        
        override fun describeTo(description: Description?) {
            description?.appendText("uri should be $uri")
        }

        override fun matches(item: Any?): Boolean {
            return when (item) {
                is HttpUriRequest -> item.uri == uri
                else -> false
            }
        }


    }
}