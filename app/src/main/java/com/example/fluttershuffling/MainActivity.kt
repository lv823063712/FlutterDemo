package com.example.fluttershuffling

import android.view.KeyEvent
import android.widget.Toast
import com.example.fluttershuffling.fragment.FlutterFragment
import com.example.fluttershuffling.fragment.OneFragment
import com.yinglan.alphatabs.AlphaTabsIndicator
import me.yokeyword.fragmentation.SupportFragment
import java.io.File

class MainActivity : BaseActivity() {
    private val mFragments: MutableList<SupportFragment> = mutableListOf()
    private var mTabPosition: Int = 0
    private var mExitTime: Long = 0
    override fun getLayout(): Int = R.layout.activity_main

    override fun initData() {
        initFragment()
        initBottomNavigationView()
    }

    override fun setClick() {
    }

    override fun preLogic() {
    }

    fun initFragment() {
        mFragments.add(OneFragment())
        mFragments.add(FlutterFragment())
        loadMultipleRootFragment(
            R.id.home_yidian_fl_container,
            0,
            mFragments[0],
            mFragments[1]
        )
    }

    fun initBottomNavigationView() {
        val bottom_navigation = findViewById<AlphaTabsIndicator>(R.id.bottom_navigation)
        bottom_navigation.setOnTabChangedListner { position ->
            showHideFragment(mFragments[position], mFragments[mTabPosition])
            mTabPosition = position
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.repeatCount == 0) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, getString(R.string.tips_global_again_exit), Toast.LENGTH_SHORT)
                    .show()
                mExitTime = System.currentTimeMillis()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}