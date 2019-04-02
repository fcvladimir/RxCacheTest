package com.test.presentation.mvp.home

import com.test.presentation.mvp.IBaseContract

interface IInfoContract {
    interface View : IBaseContract.View

    interface Presenter : IBaseContract.Presenter<View>
}