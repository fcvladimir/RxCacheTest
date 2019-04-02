package com.test.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import com.test.ui.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.android.scope.ext.android.bindScope
import org.koin.android.scope.ext.android.getOrCreateScope
import org.koin.core.parameter.parametersOf

class MenuFragment : BaseFragment(), IHomeContract.View {

    companion object {
        val TAG = MenuFragment::class.java.name
    }

    private val presenter by inject<IHomeContract.Presenter> { parametersOf(activity!!) }

    private val router by inject<IHomeRouter> { parametersOf(activity!!) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindScope(getOrCreateScope(KOIN_KEY_SCOPE_HOME_FRAGMENT))

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        vvv!!.removeAllViews()
        val currentOrientation = newConfig.orientation
        val inf =
                activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            vvv!!.addView(inf.inflate(R.layout.fragment_menu_tablet, null))
        } else {
            vvv!!.addView(inf.inflate(R.layout.fragment_menu, null))
        }

        val spCommonOffice = vvv!!.findViewById<TextView>(R.id.spCommonOffice)
        val spCommonAppeal = vvv!!.findViewById<TextView>(R.id.spCommonAppeal)

        spCommonOffice.onClick {
            val popUp = commonOfficePopup()
            popUp.showAsDropDown(spCommonOffice, 0, 0)
        }

        spCommonAppeal.onClick {
            val popUp = commonAppealPopup()
            popUp.showAsDropDown(spCommonAppeal, 0, 0)
        }
    }

    var vvv: FrameLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        retainInstance = true

        vvv = FrameLayout(activity!!)
        val currentOrientation = resources.configuration.orientation
        val inf =
                activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            vvv!!.addView(inf.inflate(R.layout.fragment_menu_tablet, null))
        } else {
            vvv!!.addView(inf.inflate(R.layout.fragment_menu, null))
        }
//        return inflater.inflate(R.layout.fragment_menu, container, false)
        return vvv
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.view = this

        spCommonOffice.onClick {
            val popUp = commonOfficePopup()
            popUp.showAsDropDown(spCommonOffice, 0, 0)
        }

        spCommonAppeal.onClick {
            val popUp = commonAppealPopup()
            popUp.showAsDropDown(spCommonAppeal, 0, 0)
        }
    }

    private fun commonOfficePopup(): PopupWindow {

        // initialize a pop up window type
        val popupWindow = PopupWindow(context)

        val stringArray = resources.getStringArray(R.array.common_office_array_menu)

        val adapter = ArrayAdapter<String>(context!!, R.layout.item_popup_dropdown,
                                           stringArray)
        // the drop down list is a list view
        val listViewSort = ListView(context!!)

        // set our adapter and pass our pop up window contents
        listViewSort.adapter = adapter

        // set on item selected
        listViewSort.onItemClickListener =
                AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
                    when (p2) {
                        COMMON_OFFICE_SEARCH -> {
                            router.openDocumentChooseScreen()
                        }
                        COMMON_OFFICE_EXECUTION -> {
                        }
                        COMMON_OFFICE_SIGNATURE -> {
                            router.openSignatureLoginScreen(BUNDLE_SIGNATURE_DOCUMENT)
                        }
                    }
                    popupWindow.dismiss()
                }

        // some other visual settings for popup window
        popupWindow.isFocusable = true
        popupWindow.width = spCommonOffice.width
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT

        // set the listview as popup content
        popupWindow.contentView = listViewSort

        return popupWindow
    }

    private fun commonAppealPopup(): PopupWindow {

        // initialize a pop up window type
        val popupWindow = PopupWindow(context)

        val stringArray = resources.getStringArray(R.array.common_appeal_array_menu)

        val adapter = ArrayAdapter<String>(context!!, R.layout.item_popup_dropdown,
                                           stringArray)
        // the drop down list is a list view
        val listViewSort = ListView(context!!)

        // set our adapter and pass our pop up window contents
        listViewSort.adapter = adapter

        // set on item selected
        listViewSort.onItemClickListener =
                AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
                    when (p2) {
                        COMMON_APPEAL_SEARCH -> {
                            router.openAppealChooseScreen()
                        }
                        COMMON_APPEAL_EXECUTION -> {
                        }
                        COMMON_APPEAL_SIGNATURE -> {
                            router.openSignatureLoginScreen(BUNDLE_SIGNATURE_APPEAL)
                        }
                    }
                    popupWindow.dismiss()
                }

        // some other visual settings for popup window
        popupWindow.isFocusable = true
        popupWindow.width = spCommonAppeal.width
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT

        // set the listview as popup content
        popupWindow.contentView = listViewSort

        return popupWindow
    }

    override fun renderNetworkProcessing(processing: Boolean) {
        if (processing) {
            showLoading()
        } else {
            hideLoading()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CHECK_LOGIN_REQUEST -> {
                    router.openSignatureScreen()
                }
            }
        }
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