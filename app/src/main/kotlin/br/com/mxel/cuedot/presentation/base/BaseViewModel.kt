package br.com.mxel.cuedot.presentation.base

import androidx.lifecycle.ViewModel
import br.com.mxel.cuedot.domain.Event
import br.com.mxel.cuedot.domain.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(
        protected val scheduler: SchedulerProvider
) : ViewModel() {

    protected val disposable = CompositeDisposable()

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

    protected fun checkIfCanDispose(event: Event<Any>) {
        if (event.isData() || event.isError()) {
            disposable.clear()
        }
    }
}