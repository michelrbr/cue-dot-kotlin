package br.com.mxel.cuedot

import android.content.Intent
import android.os.Bundle
import br.com.mxel.cuedot.presentation.base.BaseActivity
import br.com.mxel.cuedot.presentation.orderby.OrderedByActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, OrderedByActivity::class.java))
    }
}
