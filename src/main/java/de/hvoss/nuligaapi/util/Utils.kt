package de.hvoss.nuligaapi.util

import java.net.URI
import java.net.URISyntaxException



fun retrieveEnvOrThrow(name: String): String {
    return java.lang.System.getenv(name) ?: throw IllegalStateException("Environment variable [$name] is not set.")
}

fun retrieveUriFromEnc(name: String) : URI {
    try {
        return URI(retrieveEnvOrThrow(name))
    } catch (e: URISyntaxException) {
        throw RuntimeException(e)
    }

}