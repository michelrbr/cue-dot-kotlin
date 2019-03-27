package br.com.mxel.cuedot.presentation

import android.os.Bundle
import br.com.mxel.cuedot.R
import br.com.mxel.cuedot.domain.orderby.Order
import br.com.mxel.cuedot.presentation.base.BaseActivity
import br.com.mxel.cuedot.presentation.orderby.OrderedByActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        OrderedByActivity.launch(this, Order.POPULAR)
    }
}
