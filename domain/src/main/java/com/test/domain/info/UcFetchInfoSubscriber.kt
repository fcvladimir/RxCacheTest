package com.test.domain.info

import com.test.domain.IRepository
import com.test.domain.base.AbsObsevableUc
import com.test.domain.model.Info
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class UcFetchInfoSubscriber(
        compositeDisposable: CompositeDisposable,
        scheduler: Scheduler,
        private val resolutionRepository: IRepository<Info>
) : AbsObsevableUc<Unit, Info>(compositeDisposable, scheduler) {

    override fun buildUc(param: Unit): Observable<Info> {
        return resolutionRepository.observe(IRepository.Observe.FetchInfoSubscriber())
    }

}