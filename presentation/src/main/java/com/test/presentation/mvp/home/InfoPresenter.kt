package com.test.presentation.mvp.home

class InfoPresenter : IInfoContract.Presenter {

    override var view: IInfoContract.View? = null

    override fun onUnsubscribe() {
    }

    override fun onDestroy() {
    }

}