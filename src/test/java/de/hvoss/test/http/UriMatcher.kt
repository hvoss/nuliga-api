package de.hvoss.test.http

import org.apache.http.client.methods.HttpUriRequest
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.slf4j.LoggerFactory
import java.net.URI

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