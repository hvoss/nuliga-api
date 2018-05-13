package de.hvoss.nuligaapi.config

import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringConfig {

    @Bean
    fun httpClient() : CloseableHttpClient {
        val timeoutInSeconds = 30;
        val httpConfig = RequestConfig.custom()
                .setConnectTimeout(timeoutInSeconds * 1000)
                .setConnectionRequestTimeout(timeoutInSeconds * 1000)
                .setSocketTimeout(timeoutInSeconds * 1000).build()

        return HttpClientBuilder.create().setDefaultRequestConfig(httpConfig).build()
    }
}