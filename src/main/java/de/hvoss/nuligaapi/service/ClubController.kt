package de.hvoss.nuligaapi.service

import de.hvoss.nuligaapi.model.Club
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController("clubs")
class ClubController {

    @RequestMapping
    fun listClubs() : List<Club> {
        return Arrays.asList(Club(name= "sddf"))
    }
}