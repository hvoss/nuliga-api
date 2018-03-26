package de.hvoss.nuligaapi.dataaccess

import de.hvoss.nuligaapi.model.Club
import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.data.rest.core.config.Projection

@RepositoryRestResource(collectionResourceRel = "club", path = "clubs")
interface ClubRepository : CrudRepository<Club, String> {}
