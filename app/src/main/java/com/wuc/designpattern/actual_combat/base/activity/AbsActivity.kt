package com.wuc.designpattern.actual_combat.base.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.wuc.designpattern.actual_combat.observe_network_change.NetWorkMonitorManager

/**
 * @author: wuc
 * @date: 2024/9/9
 * @desc:最底层的Activity,不带MVP和MVVM,一般不用这个
 */
abstract class AbsActivity : AppCompatActivity(), NetWorkMonitorManager.NetworkConnectionChangedListener {
    private val TAG: String
        get() = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentLayout()
        initView(savedInstanceState)
        initData()
        if (needRegisterNetworkChangeObserver()) {
            NetWorkMonitorManager.registerObserver(this@AbsActivity)
        }
    }

    /**
     * 设置布局
     */
    open fun setContentLayout() {
        setContentView(getLayoutResId())
    }

    /**
     * 初始化视图
     * @return Int 布局id
     * 仅用于不继承BaseDataBindActivity类的传递布局文件
     */
    abstract fun getLayoutResId(): Int

    /**
     * 初始化布局
     * @param savedInstanceState Bundle?
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 初始化数据
     */
    open fun initData() {

    }

    /**
     * 是否需要注册监听网络变换
     */
    protected open fun needRegisterNetworkChangeObserver(): Boolean {
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (needRegisterNetworkChangeObserver()) {
            NetWorkMonitorManager.unregisterObserver(this@AbsActivity)
        }
    }
}