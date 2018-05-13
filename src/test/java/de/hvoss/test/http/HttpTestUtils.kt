package de.hvoss.test.http

import org.apache.http.client.HttpClient
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.entity.StringEntity
import org.mockito.BDDMockito.argThat
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.net.URI
import java.nio.file.Files
import java.nio.file.Paths

fun HttpClient.mockHttpGet(url: String, localeFile: String) {
    val file = String(
            Files.readAllBytes(
                    Paths.get(UriMatcher::class.java.classLoader.getResource(localeFile).toURI())
            ),
            charset("UTF-8")
    );

    val entity = StringEntity(file)
    val response = Mockito.mock(CloseableHttpResponse::class.java)

    given(execute(argThat(UriMatcher(URI(url))))).willReturn(response)
    given(response.entity).willReturn(entity)
}