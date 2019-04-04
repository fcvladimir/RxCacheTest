package com.test.presentation.mvp.home

import com.test.domain.model.Info
import com.test.presentation.mvp.IBaseContract

interface IInfoContract {
    interface View : IBaseContract.View {

        fun showInfo(info: Info)
    }

    interface Presenter : IBaseContract.Presenter<View> {

        fun fetchInfo()

        fun fetchInfoSubscriber()
    }
}