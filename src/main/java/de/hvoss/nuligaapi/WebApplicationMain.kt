package de.hvoss.nuligaapi

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
open class WebApplicationMain

fun main(args: Array<String>) {
    SpringApplication.run(WebApplicationMain::class.java, *args)
}