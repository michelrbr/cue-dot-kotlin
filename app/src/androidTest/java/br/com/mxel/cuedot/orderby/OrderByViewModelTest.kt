package br.com.mxel.cuedot.orderby

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.mxel.cuedot.presentation.orderby.OrderedByViewModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.android.viewmodel.ext.koin.getViewModel
import org.koin.standalone.KoinComponent

@RunWith(AndroidJUnit4::class)
class OrderByViewModelTest: KoinComponent {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var lifecycleOwner: FragmentActivity

    @Before
    fun setup() = MockKAnnotations.init(this)

    @After
    fun shutdown() = unmockkAll()

    @Test
    fun testOrderByUseCaseInjection() {

        val viewModel:OrderedByViewModel = getViewModel(lifecycleOwner)

        checkNotNull(viewModel)
    }
}