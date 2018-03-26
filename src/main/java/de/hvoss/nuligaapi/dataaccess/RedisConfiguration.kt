package de.hvoss.nuligaapi.dataaccess

import de.hvoss.nuligaapi.util.retrieveUriFromEnc
import redis.clients.jedis.JedisPoolConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import java.net.URI

@Configuration
open class RedisConfiguration {

    @Bean
    open fun jedisConnectionFactory(): JedisConnectionFactory {
        val poolConfig = JedisPoolConfig()
        poolConfig.maxIdle = 5
        poolConfig.minIdle = 1
        poolConfig.testOnBorrow = true
        poolConfig.testOnReturn = true
        poolConfig.testWhileIdle = true

        val uri = retrieveUriFromEnc("REDIS_URL")

        val standaloneConfig = RedisStandaloneConfiguration(uri.host, uri.port)
        standaloneConfig.password = RedisPassword.of(uri.userInfo.split(":")[1])

        val clientConfig = JedisClientConfiguration.builder()
                                                                       .usePooling()
                                                                       .poolConfig(poolConfig)
                                                                       .build();

        return JedisConnectionFactory(standaloneConfig, clientConfig)
    }

    @Bean
    open fun redisTemplate(): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.connectionFactory = jedisConnectionFactory()
        return template
    }
}