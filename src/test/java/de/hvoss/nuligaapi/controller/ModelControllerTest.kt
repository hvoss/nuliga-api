package de.hvoss.nuligaapi.controller

import de.hvoss.nuligaapi.dataaccess.ClubRepository
import de.hvoss.nuligaapi.nuliga.client.NuLigaAccess
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
    lateinit var nuLigaAccess : NuLigaAccess

    lateinit var sut : ModelController

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        sut = ModelController(clubRepository = clubRepository, nuLigaAccess = nuLigaAccess)
    }


}