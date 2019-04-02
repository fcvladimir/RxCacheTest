package com.test.ui.activity

import android.os.Bundle
import com.test.R
import com.test.ui.BaseActivity
import com.test.ui.fragment.InfoFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, InfoFragment(), InfoFragment.TAG)
                    .commit()
        }
    }

}
