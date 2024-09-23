package com.wuc.designpattern.actual_combat.popup_engine

import android.app.ProgressDialog.show
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lxj.xpopup.util.XPopupUtils
import com.wuc.designpattern.R
import com.wuc.baselib.base.activity.BaseViewBindingReflectActivity
import com.wuc.designpattern.actual_combat.ext.showPopup
import com.wuc.designpattern.actual_combat.popup_engine.animation_strategy_factory.PopupAnimationType
import com.wuc.designpattern.actual_combat.popup_engine.builder.DialogEngine
import com.wuc.designpattern.actual_combat.popup_engine.factory.PopupType
import com.wuc.designpattern.databinding.ActivityPopupEngineBinding
import com.wuc.designpattern.databinding.PopupBottomLayoutBinding
import com.wuc.designpattern.databinding.PopupCenterLayoutBinding
import com.wuc.designpattern.databinding.PopupFullscreenLayoutBinding

class PopupEngineActivity : BaseViewBindingReflectActivity<ActivityPopupEngineBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.btnBottomPopup.setOnClickListener {
            DialogEngine<PopupBottomLayoutBinding>(this)
                .setViewBinding(PopupBottomLayoutBinding::inflate)
                .setWidth(XPopupUtils.getScreenWidth(this))
                .setPopupType(PopupType.BOTTOM)
                .setOnCreateListener { binding, controller ->
                    binding.tvDismiss.setOnClickListener {
                        controller.dismiss()
                    }
                }
                .show()
        }
        mBinding.btnCenterPopup.setOnClickListener {
            showPopup(
                popupType =PopupType.CENTER,
                viewBinding = PopupCenterLayoutBinding::inflate,
                onCreateListener = { binding, controller ->
                    binding.tvCancel.setOnClickListener {
                        controller.dismiss()
                    }
                    binding.tvConfirm.setOnClickListener {
                        controller.dismiss()
                    }
                }
            )
        }
        mBinding.btnFullPopup.setOnClickListener {
            showPopup(
                popupType = PopupType.FULL_SCREEN,
                viewBinding = PopupFullscreenLayoutBinding::inflate,
                popupAnimationType = PopupAnimationType.TranslateAlphaFromRight,
                onCreateListener = { binding, controller ->
                }
            )
        }
    }
}