package de.hvoss.nuligaapi.dataaccess

import de.hvoss.nuligaapi.model.Club
import de.hvoss.nuligaapi.model.Referee
import io.swagger.annotations.Api
import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.data.rest.core.config.Projection

@Api
@RepositoryRestResource(collectionResourceRel = "club", path = "clubs")
interface ClubRepository : CrudRepository<Club, String> {}


@Api
@RepositoryRestResource(collectionResourceRel = "referee", path = "referees")
interface RefereeRepository : CrudRepository<Referee, String> {}



