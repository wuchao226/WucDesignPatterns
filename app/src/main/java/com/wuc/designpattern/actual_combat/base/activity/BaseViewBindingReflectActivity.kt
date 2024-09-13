package com.wuc.designpattern.actual_combat.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.wuc.designpattern.actual_combat.ext.saveAs
import com.wuc.designpattern.actual_combat.ext.saveAsUnChecked
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

/**
 * @author: wuc
 * @date: 2024/9/9
 * @desc: 通过反射 封装ViewBinding Activity基类
 */
abstract class BaseViewBindingReflectActivity<VB : ViewBinding> : AbsActivity() {

    private var _binding: VB? = null
    protected val mBinding: VB
        get() = requireNotNull(_binding) { "ViewBinding 对象为空，视图可能尚未创建或已销毁" }

    // 缓存反射结果: 可以通过缓存反射调用结果，减少多次反射带来的性能开销。
    // 使用 lazy 来保证方法只初始化一次，避免不必要的检查
    private val inflateMethodCache: Method? by lazy {
        val type: ParameterizedType = javaClass.genericSuperclass as ParameterizedType
        val vbClass: Class<VB> = type.saveAs<ParameterizedType>().actualTypeArguments[0].saveAs()
        try {
            vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
            null // 方法未找到
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            null // 方法无法访问
        }
    }

    override fun setContentLayout() {
        _binding = createViewBinding()
        setContentView(mBinding.root)
    }

    private fun createViewBinding(): VB {
        return try {
            inflateMethodCache?.invoke(null, layoutInflater)?.saveAsUnChecked()
                ?: throw RuntimeException("ViewBinding inflate 方法调用失败")
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
            throw RuntimeException("反射调用 ViewBinding.inflate 方法时出错", e)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            throw RuntimeException("无法访问 ViewBinding.inflate 方法", e)
        } catch (e: ClassCastException) {
            e.printStackTrace()
            throw RuntimeException("无法将结果转换为 ViewBinding", e)
        }
    }

    override fun getLayoutResId(): Int = 0
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}