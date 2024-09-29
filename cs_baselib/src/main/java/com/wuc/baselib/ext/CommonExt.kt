package com.wuc.baselib.ext

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wuc.baselib.base.BaseApplication
import com.wuc.baselib.utils.CommUtils
import com.wuc.baselib.utils.NetWorkUtil
import java.io.Serializable
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

/**
 * @author: wuc
 * @date: 2024/9/23
 * @desc:
 */
/**
 *  通用扩展
 */
/**
 * openActivity<TestActivity>(context) {
 *     putExtra("param1", "data")
 *     putExtra("param2", 123)
 * }
 */
inline fun <reified T> openActivity(context: Context, noinline block: (Intent.() -> Unit)? = null) {
    val intent = Intent(context, T::class.java)
    block?.invoke(intent)
    context.startActivity(intent)
}

/**
 * 全局的Context
 */
fun Any.commContext(): Context {
    return CommUtils.context
}

/** 动态创建Drawable **/
fun Context.createDrawable(
    color: Int = Color.TRANSPARENT, radius: Float = 0f,
    strokeColor: Int = Color.TRANSPARENT, strokeWidth: Int = 0,
    enableRipple: Boolean = true,
    rippleColor: Int = Color.parseColor("#88999999")
): Drawable {
    val content = GradientDrawable().apply {
        setColor(color)
        cornerRadius = radius
        setStroke(strokeWidth, strokeColor)
    }
    if (Build.VERSION.SDK_INT >= 21 && enableRipple) {
        return RippleDrawable(ColorStateList.valueOf(rippleColor), content, null)
    }
    return content
}

fun Fragment.createDrawable(
    color: Int = Color.TRANSPARENT, radius: Float = 0f,
    strokeColor: Int = Color.TRANSPARENT, strokeWidth: Int = 0,
    enableRipple: Boolean = true,
    rippleColor: Int = Color.parseColor("#88999999")
): Drawable {
    return context!!.createDrawable(
        color,
        radius,
        strokeColor,
        strokeWidth,
        enableRipple,
        rippleColor
    )
}

fun View.createDrawable(
    color: Int = Color.TRANSPARENT, radius: Float = 0f,
    strokeColor: Int = Color.TRANSPARENT, strokeWidth: Int = 0,
    enableRipple: Boolean = true,
    rippleColor: Int = Color.parseColor("#88999999")
): Drawable {
    return context!!.createDrawable(
        color,
        radius,
        strokeColor,
        strokeWidth,
        enableRipple,
        rippleColor
    )
}

fun RecyclerView.ViewHolder.createDrawable(
    color: Int = Color.TRANSPARENT, radius: Float = 0f,
    strokeColor: Int = Color.TRANSPARENT, strokeWidth: Int = 0,
    enableRipple: Boolean = true,
    rippleColor: Int = Color.parseColor("#88999999")
): Drawable {
    return itemView.createDrawable(
        color,
        radius,
        strokeColor,
        strokeWidth,
        enableRipple,
        rippleColor
    )
}


/** toast相关 **/
fun Any.toast(msg: String?) {
    ToastUtils.makeText(CommUtils.context, msg)
}

fun Any.toast(res: Int) {
    ToastUtils.makeText(CommUtils.context, res)
}

fun Any.toastError(msg: String?) {
    ToastUtils.showFailText(CommUtils.context, msg)
}

fun Any.toastError(res: Int) {
    ToastUtils.showFailText(CommUtils.context, res)
}

fun Any.toastSuccess(msg: String?) {
    ToastUtils.showSuccessText(CommUtils.context, msg)
}

fun Any.toastSuccess(res: Int) {
    ToastUtils.showSuccessText(CommUtils.context, res)
}


/** json相关 **/
fun Any.toJson() = Gson().toJson(this)

//内联函数+标注泛型 = 泛型实例化
inline fun <reified T> String.toBean() = Gson().fromJson<T>(this, object : TypeToken<T>() {}.type)


/** Window相关 **/
fun Context.windowWidth(): Int {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return windowManager.defaultDisplay.width
}

fun Context.windowHeight(): Int {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return windowManager.defaultDisplay.height
}

fun Fragment.windowWidth(): Int {
    return context!!.windowWidth()
}

fun Fragment.windowHeight(): Int {
    return context!!.windowHeight()
}

fun View.windowWidth(): Int {
    return context!!.windowWidth()
}

fun View.windowHeight(): Int {
    return context!!.windowHeight()
}

fun RecyclerView.ViewHolder.windowWidth(): Int {
    return itemView.windowWidth()
}

fun RecyclerView.ViewHolder.windowHeight(): Int {
    return itemView.windowHeight()
}


/** 网络相关 **/
/**
 * 当前网络是否有连接
 */
fun Any.isNetworkConnected() = NetWorkUtil.isConnected(CommUtils.context)

/**
 * 当前是否是Wifi连接
 */
fun Any.isWifiConnected() = NetWorkUtil.isWifiConnected(CommUtils.context)


/**
 * 数组转bundle
 */
fun Array<out Pair<String, Any?>>.toBundle(): Bundle? {
    return Bundle().apply {
        forEach { it ->
            val value = it.second
            when (value) {
                null -> putSerializable(it.first, null as Serializable?)
                is Int -> putInt(it.first, value)
                is Long -> putLong(it.first, value)
                is CharSequence -> putCharSequence(it.first, value)
                is String -> putString(it.first, value)
                is Float -> putFloat(it.first, value)
                is Double -> putDouble(it.first, value)
                is Char -> putChar(it.first, value)
                is Short -> putShort(it.first, value)
                is Boolean -> putBoolean(it.first, value)
                is Serializable -> putSerializable(it.first, value)
                is Parcelable -> putParcelable(it.first, value)

                is IntArray -> putIntArray(it.first, value)
                is LongArray -> putLongArray(it.first, value)
                is FloatArray -> putFloatArray(it.first, value)
                is DoubleArray -> putDoubleArray(it.first, value)
                is CharArray -> putCharArray(it.first, value)
                is ShortArray -> putShortArray(it.first, value)
                is BooleanArray -> putBooleanArray(it.first, value)

                is Array<*> -> when {
                    value.isArrayOf<CharSequence>() -> putCharSequenceArray(
                        it.first,
                        value as Array<CharSequence>
                    )
                    value.isArrayOf<String>() -> putStringArray(it.first, value as Array<String>)
                    value.isArrayOf<Parcelable>() -> putParcelableArray(
                        it.first,
                        value as Array<Parcelable>
                    )
                }
            }
        }
    }

}

//data class 对象的深拷贝
fun <T : Any> T.deepCopy(): T {
    //如果不是数据类，直接返回
    if (!this::class.isData) {
        return this
    }

    //拿到构造函数
    return this::class.primaryConstructor!!.let { primaryConstructor ->
        primaryConstructor.parameters.map { parameter ->
            //转换类型

            //最终value=第一个参数类型的对象
            val value = (this::class as KClass<T>).memberProperties.first {
                it.name == parameter.name
            }.get(this)

            //如果当前类(这里的当前类指的是参数对应的类型，比如说这里如果非基本类型时)是数据类
            if ((parameter.type.classifier as? KClass<*>)?.isData == true) {
                parameter to value?.deepCopy()
            } else {
                parameter to value
            }

            //最终返回一个新的映射map,即返回一个属性值重新组合的map，并调用callBy返回指定的对象
        }.toMap().let(primaryConstructor::callBy)
    }
}


/**
 * 主线程运行
 */
fun Any.runOnUIThread(block: () -> Unit) {
    Handler(Looper.getMainLooper()).post { block() }
}


/**
 * 检查是否有网络-直接检查全局内存
 */
fun Any.checkNet(
    block: () -> Unit,
    msg: String = "Network connection error, please check the network connection"
) {
    if (BaseApplication.checkHasNet()) {
        block()
    } else {
        toast(msg)
    }

}