package de.hvoss.nuligaapi.controller

import de.hvoss.nuligaapi.dataaccess.NuLigaAccessImpl
import de.hvoss.nuligaapi.dataaccess.NuLigaTestDAO
import org.junit.Assert.*
import org.junit.Test

class ModelControllerTest {


    @Test
    fun readNuLiga() {
        val dao = NuLigaTestDAO()
        val access = NuLigaAccessImpl(dao)
        val controller = ModelController(access)
        controller.buildModel()
    }
}