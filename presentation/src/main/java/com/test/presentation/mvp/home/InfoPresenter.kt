package com.test.presentation.mvp.home

import com.test.domain.info.UcFetchInfo
import com.test.domain.info.UcFetchInfoSubscriber

class InfoPresenter(
        private val fetchInfo: UcFetchInfo,
        private val fetchInfoSubscriber: UcFetchInfoSubscriber
) : IInfoContract.Presenter {

    override var view: IInfoContract.View? = null

    override fun fetchInfo() {
        fetchInfo.execute(
                onSuccess = {
                    view?.showInfo(it)
                },
                param = Unit
        )
    }

    override fun fetchInfoSubscriber() {
        fetchInfoSubscriber.execute(onNext = {
            view?.showInfo(it)
        }, param = Unit)
    }



    override fun onUnsubscribe() {
        fetchInfo.clear()
    }

    override fun onDestroy() {
        fetchInfo.dispose()
    }

}