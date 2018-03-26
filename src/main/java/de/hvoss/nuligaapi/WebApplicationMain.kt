package de.hvoss.nuligaapi

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import
import org.springframework.scheduling.annotation.EnableScheduling
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration

@SpringBootApplication
@EnableScheduling
@Import(SpringDataRestConfiguration::class)
open class WebApplicationMain

fun main(args: Array<String>) {
    SpringApplication.run(WebApplicationMain::class.java, *args)
}