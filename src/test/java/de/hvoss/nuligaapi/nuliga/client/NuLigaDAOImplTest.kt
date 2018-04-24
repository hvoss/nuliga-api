package de.hvoss.nuligaapi.nuliga.client

import org.junit.Before
import org.junit.Test

class NuLigaDAOImplTest{

    private lateinit var sut : NuLigaDAOImpl

    @Before
    fun setup() {
        sut = NuLigaDAOImpl()
    }

    @Test
    fun testLoadClubSearch() {
        val list = sut.loadClubSearch().forEach{ l -> System.out.println(l) }
    }
}