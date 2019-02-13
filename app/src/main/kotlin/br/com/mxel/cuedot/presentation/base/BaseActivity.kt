package br.com.mxel.cuedot.presentation.base

import androidx.appcompat.app.AppCompatActivity
import butterknife.Unbinder
import io.reactivex.disposables.CompositeDisposable

open class BaseActivity: AppCompatActivity() {

    protected val disposable = CompositeDisposable()

    protected var unbinder: Unbinder? = null

    override fun onDestroy() {
        disposable.clear()
        unbinder?.unbind()
        super.onDestroy()
    }
}