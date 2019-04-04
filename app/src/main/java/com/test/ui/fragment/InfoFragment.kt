package com.test.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.R
import com.test.di.KOIN_KEY_SCOPE_INFO_FRAGMENT
import com.test.domain.model.Info
import com.test.presentation.mvp.home.IInfoContract
import com.test.ui.BaseFragment
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import org.koin.android.scope.ext.android.bindScope
import org.koin.android.scope.ext.android.getOrCreateScope
import org.koin.core.parameter.parametersOf

class InfoFragment : BaseFragment(), IInfoContract.View {

    companion object {
        val TAG = InfoFragment::class.java.name
    }

    private val presenter by inject<IInfoContract.Presenter> { parametersOf(activity!!) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindScope(getOrCreateScope(KOIN_KEY_SCOPE_INFO_FRAGMENT))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.view = this

        presenter.fetchInfo()
    }

    override fun showInfo(info: Info) {
        context?.toast("HERE")
    }

    override fun onDestroyView() {
        presenter.view = null
        presenter.onUnsubscribe()
        super.onDestroyView()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}