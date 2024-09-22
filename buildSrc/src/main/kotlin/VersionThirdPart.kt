/**
 * @author: wuc
 * @date: 2024/9/20
 * @desc: 第三方依赖包
 */
object VersionThirdPart {
    object ARouter {
        private const val version = "1.0.3"
        //  还有其他的fork库
        //  https://github.com/aleyn97/router
        //  https://github.com/jadepeakpoet/ARouter/blob/develop/README_CN.md
        //  https://github.com/wyjsonGo/GoRouter
        //  也可以参照自行修改 https://juejin.cn/post/7222091234100330554 ，https://juejin.cn/post/7280436457135144999
        //core
        const val core = "com.github.jadepeakpoet.ARouter:arouter-api:$version"

        //注解处理
        const val compiler = "com.github.jadepeakpoet.ARouter:arouter-compiler:$version"

        // plugin
        const val plugin = "com.github.jadepeakpoet.ARouter:arouter-register:$version"
    }

    //网络请求 retrofit
    object Retrofit {
        private const val version = "2.9.0"

        //内置了OkHttp 3.14.9 与 okio 3.1.0
        const val core = "com.squareup.retrofit2:retrofit:$version"

        //gson转换器
        const val convertGson = "com.squareup.retrofit2:converter-gson:$version"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:$version"

        //指定Gson版本
        const val gson = "com.google.code.gson:gson:2.10.1"
    }

    //图片加载框架
    object Glide {
        private const val version = "4.11.0"

        const val core = "com.github.bumptech.glide:glide:$version"
        const val annotation = "com.github.bumptech.glide:annotations:$version"
        const val integration = "com.github.bumptech.glide:okhttp3-integration:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
    }

    //多媒体 https://github.com/LuckSiege/PictureSelector
    object ImageSelector {
        private const val version = "v3.11.2"

        const val core = "io.github.lucksiege:pictureselector:$version"
        const val compress = "io.github.lucksiege:compress:$version"
        const val ucrop = "io.github.lucksiege:ucrop:$version"
    }

    //第三方 dialog 项目地址：https://github.com/li-xiaojun/XPopup
    object XPopup {
        private const val version = "2.10.0"
        private const val picker_version = "1.0.1"

        //弹窗
        const val core = "com.github.li-xiaojun:XPopup:$version"

        //弹窗+PickerView封装
        const val picker = "com.github.li-xiaojun:XPopupExt:$picker_version"

        //简易RV-Adapter
        const val easyAdapter = "com.github.li-xiaojun:EasyAdapter:1.2.8"
    }

    //第三方刷新布局 https://github.com/scwang90/SmartRefreshLayout
    object SmartRefresh {
        private const val version = "2.1.0"

        //刷新布局
        const val core = "io.github.scwang90:refresh-layout-kernel:$version"

        //经典刷新头布局
        const val classicsHeader = "io.github.scwang90:refresh-header-classics:$version"
    }

    //气泡吐司  https://github.com/getActivity/Toaster
    const val toaster = "com.github.getActivity:Toaster:12.6"

    //权限申请  https://github.com/getActivity/XXPermissions
    const val xxPermission = "com.github.getActivity:XXPermissions:18.6"

    //Gson 解析容错  https://github.com/getActivity/GsonFactory
    const val gsonFactory = "com.github.getActivity:GsonFactory:9.5"

    //第三方轮播图  https://github.com/youth5201314/banner
    const val banner = "io.github.youth5201314:banner:2.2.3"

    //RecycleView适配器工具  https://github.com/CymChad/BaseRecyclerViewAdapterHelper
    const val baseRecycleViewHelper = "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.4"

    //LiveBus - 消息总线  https://github.com/JeremyLiao/LiveEventBus
    const val liveEventBus = "io.github.jeremyliao:live-event-bus-x:1.8.0"

    //lottie 动画
    // 你可以使用转换工具将mp4转换成json【https://isotropic.co/video-to-lottie/】
    // 去这里下载素材【https://lottiefiles.com/】
    const val lottie = "com.airbnb.android:lottie:4.1.0"

    //内存泄露检测工具
    const val leakcanary = "com.squareup.leakcanary:leakcanary-android:2.7"

    //视频播放器
    object ExoPlayer {
        private const val version = "2.16.1"

        //完整依赖
        const val all = "com.google.android.exoplayer:exoplayer:$version"

        //核心依赖
        const val core = "com.google.android.exoplayer:exoplayer-core:$version"
        const val dash = "com.google.android.exoplayer:exoplayer-dash:$version"
        const val hls = "com.google.android.exoplayer:exoplayer-hls:$version"
        const val rtsp = "com.google.android.exoplayer:exoplayer-rtsp:$version"
        const val smoothStreaming = "com.google.android.exoplayer:exoplayer-smoothstreaming:$version"
        const val transformer = "com.google.android.exoplayer:exoplayer-transformer:$version"
        const val ui = "com.google.android.exoplayer:exoplayer-ui:$version"
    }
}