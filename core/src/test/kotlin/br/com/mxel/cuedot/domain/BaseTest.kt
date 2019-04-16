package br.com.mxel.cuedot.domain

import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before

abstract class BaseTest {

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        initialize()
    }

    @After
    fun shutdown() {
        unmockkAll()
        finish()
    }

    open fun initialize() {}

    open fun finish() {}
}