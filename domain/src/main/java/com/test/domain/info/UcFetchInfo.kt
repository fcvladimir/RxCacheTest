package com.test.domain.info

import com.test.domain.IRepository
import com.test.domain.base.AbsSingleUc
import com.test.domain.model.Info
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

class UcFetchInfo(
        compositeDisposable: CompositeDisposable,
        scheduler: Scheduler,
        private val resolutionRepository: IRepository<Info>
) : AbsSingleUc<Unit, Info>(compositeDisposable, scheduler) {

    override fun buildUc(param: Unit): Single<Info> {
        return resolutionRepository.get(IRepository.Get.FetchInfo())
    }

}