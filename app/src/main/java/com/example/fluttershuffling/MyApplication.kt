package com.example.fluttershuffling

import android.app.Application
import android.os.Build
import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.Utils
import com.idlefish.flutterboost.interfaces.INativeRouter
import io.flutter.embedding.android.FlutterView
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.StandardMessageCodec

/**
 * @ClassName: MyApplication
 * @Description: java类作用描述
 * @CreateDate: 2020/11/13 16:17
 * @Creator: lf
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initFlutter()
    }

    private fun initFlutter() {
        val router = INativeRouter { context, url, urlParams, requestCode, exts ->
            val assembleUrl: String = Utils.assembleUrl(url, urlParams)

            PageRouter.openPageByUrl(context, assembleUrl, urlParams)
        }

        val boostLifecycleListener: FlutterBoost.BoostLifecycleListener = object :
            FlutterBoost.BoostLifecycleListener {
            override fun beforeCreateEngine() {}
            override fun onEngineCreated() {
                // 注册MethodChannel，监听flutter侧的getPlatformVersion调用
                val methodChannel = MethodChannel(
                    FlutterBoost.instance().engineProvider().dartExecutor,
                    "flutter_native_channel"
                )
                methodChannel.setMethodCallHandler { call, result ->
                    if (call.method == "getPlatformVersion") {
                        result.success(Build.VERSION.RELEASE)
                    } else {
                        result.notImplemented()
                    }
                }

                // 注册PlatformView viewTypeId要和flutter中的viewType对应
                FlutterBoost
                    .instance()
                    .engineProvider()
                    .platformViewsController
                    .registry
                    .registerViewFactory(
                        "plugins.test/view",
                        TextPlatformViewFactory(StandardMessageCodec.INSTANCE)
                    )
            }

            override fun onPluginsRegistered() {}
            override fun onEngineDestroy() {}
        }
        //
        // AndroidManifest.xml 中必须要添加 flutterEmbedding 版本设置
        //
        //   <meta-data android:name="flutterEmbedding"
        //               android:value="2">
        //    </meta-data>
        // GeneratedPluginRegistrant 会自动生成 新的插件方式　
        //
        // 插件注册方式请使用
        // FlutterBoost.instance().engineProvider().getPlugins().add(new FlutterPlugin());
        // GeneratedPluginRegistrant.registerWith()，是在engine 创建后马上执行，放射形式调用
        //
        val platform = FlutterBoost
            .ConfigBuilder(this, router)
            .isDebug(true)
//            .dartEntrypoint()//dart入口，默认为main函数，这里可以根据native的环境自动选择Flutter的入口函数来统一Native和Flutter的执行环境，（比如debugMode == true ? "mainDev" : "mainProd"，Flutter的main.dart里也要有这两个对应的入口函数）
            .whenEngineStart(FlutterBoost.ConfigBuilder.ANY_ACTIVITY_CREATED)
            .renderMode(FlutterView.RenderMode.texture)
            .lifecycleListener(boostLifecycleListener)
            .build()
        FlutterBoost.instance().init(platform)
    }
}