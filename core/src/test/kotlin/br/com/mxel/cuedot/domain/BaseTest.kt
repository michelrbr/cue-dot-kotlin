package br.com.mxel.cuedot.domain

import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before

open class BaseTest {

    @Before
    fun setup() = MockKAnnotations.init(this)

    @After
    fun shutdown() = unmockkAll()
}