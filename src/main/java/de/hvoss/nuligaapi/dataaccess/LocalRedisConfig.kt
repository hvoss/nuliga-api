package de.hvoss.nuligaapi.dataaccess

import redis.clients.jedis.JedisPoolConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import java.net.URI

@Configuration
open class LocalRedisConfig {

    @Bean
    fun jedisConnectionFactory(): JedisConnectionFactory {
        val poolConfig = JedisPoolConfig()
        poolConfig.maxIdle = 5
        poolConfig.minIdle = 1
        poolConfig.testOnBorrow = true
        poolConfig.testOnReturn = true
        poolConfig.testWhileIdle = true
        return JedisConnectionFactory(poolConfig)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.connectionFactory = jedisConnectionFactory()
        return template
    }
}