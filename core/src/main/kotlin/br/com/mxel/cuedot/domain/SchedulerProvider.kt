package br.com.mxel.cuedot.domain

import io.reactivex.Scheduler

class SchedulerProvider(
        val mainThread: Scheduler,
        val backgroundThread: Scheduler
)