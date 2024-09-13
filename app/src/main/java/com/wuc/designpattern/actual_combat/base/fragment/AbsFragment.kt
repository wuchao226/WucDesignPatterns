package com.wuc.designpattern.actual_combat.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wuc.designpattern.actual_combat.observe_network_change.NetWorkMonitorManager

/**
 * @author: wuc
 * @date: 2024/9/12
 * @desc: Fragment 基类
 */
abstract class AbsFragment : Fragment(), NetWorkMonitorManager.NetworkConnectionChangedListener  {

    protected var TAG: String? = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (needRegisterNetworkChangeObserver()) {
            NetWorkMonitorManager.registerObserver(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getContentView(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view, savedInstanceState)
        initData()
    }

    /**
     * 设置布局View
     */
    open fun getContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(getLayoutResId(), null)
    }

    /**
     * 初始化视图
     * @return Int 布局id
     * 仅用于不继承BaseDataBindFragment类的传递布局文件
     */
    abstract fun getLayoutResId(): Int

    /**
     * 初始化布局
     * @param savedInstanceState Bundle?
     */
    abstract fun initView(view: View, savedInstanceState: Bundle?)

    /**
     * 初始化数据
     */
    open fun initData() {}

    /**
     * 是否需要注册监听网络变换
     */
    protected open fun needRegisterNetworkChangeObserver(): Boolean {
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (needRegisterNetworkChangeObserver()) {
            NetWorkMonitorManager.unregisterObserver(this)
        }
    }
}