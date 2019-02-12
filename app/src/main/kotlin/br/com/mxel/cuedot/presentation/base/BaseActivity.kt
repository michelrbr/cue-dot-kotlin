package br.com.mxel.cuedot.presentation.base

import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

class BaseActivity: AppCompatActivity() {

    protected val disposable = CompositeDisposable()

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }
}