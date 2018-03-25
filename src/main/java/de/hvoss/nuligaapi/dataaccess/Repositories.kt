package de.hvoss.nuligaapi.dataaccess

import de.hvoss.nuligaapi.model.Club
import org.springframework.data.repository.CrudRepository


interface ClubRepository : CrudRepository<Club, String>