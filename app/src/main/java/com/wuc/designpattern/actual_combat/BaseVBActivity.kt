package com.wuc.designpattern.actual_combat

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.wuc.designpattern.databinding.ActivityNetWorkBinding.inflate

/**
 * @author: wuc
 * @date: 2024/9/9
 * @desc:
 */
abstract class BaseVBActivity<VB: ViewBinding> : AbsActivity() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //关键代码
        _binding = getViewBinding()
        setContentView(binding.root)
    }
    //关键代码
    protected abstract fun getViewBinding(): VB
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}