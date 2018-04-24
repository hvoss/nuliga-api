package de.hvoss.nuligaapi.controller

import de.hvoss.nuligaapi.dataaccess.ClubRepository
import de.hvoss.nuligaapi.dataaccess.RefereeRepository
import de.hvoss.nuligaapi.nuliga.client.NuLigaAccess
import de.hvoss.nuligaapi.nuliga.client.NuLigaAccessImpl
import de.hvoss.nuligaapi.nuliga.client.NuLigaTestDAO
import org.junit.Test
import org.mockito.Mockito.*

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ModelControllerTest {

    @Mock
    lateinit var clubRepository : ClubRepository

    @Mock
    lateinit var refereeRepository : RefereeRepository

    lateinit var sut : ModelController

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val dao = NuLigaTestDAO()
        val access = NuLigaAccessImpl(dao)
        sut = ModelController(nuLigaAccess = access, clubRepository = clubRepository, refereeRepository = refereeRepository)
    }

    @Test
    fun test() {
        sut.updateData()
    }

}