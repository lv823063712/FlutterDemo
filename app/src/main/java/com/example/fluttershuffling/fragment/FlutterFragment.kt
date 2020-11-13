package com.example.fluttershuffling.fragment

import com.example.fluttershuffling.BaseFragment
import com.example.fluttershuffling.R
import com.idlefish.flutterboost.containers.FlutterFragment

class FlutterFragment : BaseFragment() {
    override fun getLayout(): Int = R.layout.fragment_flutter

    override fun initView() {
        var map = HashMap<String, String>()
        map.put("test", "test1")
        val mFragment =
            FlutterFragment.NewEngineFragmentBuilder()
                .url("flutterPage")
                .params(map)
                .build<FlutterFragment>()
        var beginTransaction = childFragmentManager.beginTransaction()
        beginTransaction.add(R.id.myFlutter, mFragment).commit()
        beginTransaction.show(mFragment)
    }

    override fun loadData() {
    }

    override fun initData() {
    }

}