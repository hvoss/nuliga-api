package de.hvoss.nuligaapi.config

import de.hvoss.nuligaapi.util.retrieveUriFromEnc
import org.springframework.boot.autoconfigure.session.SessionProperties
import redis.clients.jedis.JedisPoolConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

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

//        Spring Boot 2
//        val standaloneConfig = RedisStandaloneConfiguration(uri.host, uri.port)
//        standaloneConfig.password = RedisPassword.of(uri.userInfo.split(":")[1])
//
//        val clientConfig = JedisClientConfiguration.builder()
//                                                                       .usePooling()
//                                                                       .poolConfig(poolConfig)
//                                                                       .build();

        val factory = JedisConnectionFactory()
        factory.hostName = uri.host
        factory.port = uri.port
        factory.password = uri.userInfo.split(":")[1]
        factory.poolConfig = poolConfig
        return factory
    }

    @Bean
    open fun redisTemplate(): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.connectionFactory = jedisConnectionFactory()
        return template
    }
}