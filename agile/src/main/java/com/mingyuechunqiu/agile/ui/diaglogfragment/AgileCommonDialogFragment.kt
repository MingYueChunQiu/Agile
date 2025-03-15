package com.mingyuechunqiu.agile.ui.diaglogfragment

import android.graphics.Color
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.mingyuechunqiu.agile.constants.AgileKeyPrefixConstants
import com.mingyuechunqiu.agile.databinding.AgileDialogFragmentCommonBinding
import com.mingyuechunqiu.agile.feature.helper.getParcelableData
import com.mingyuechunqiu.agile.framework.ui.IFragmentInflateLayoutViewCreator

/**
 * <pre>
 *      Project:    DDBookkeeping
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2024/8/26 22:21
 *      Desc:       通用对话框
 *      Version:    1.0
 * </pre>
 */
class AgileCommonDialogFragment : BaseDialogFragment() {

    private var _binding: AgileDialogFragmentCommonBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NO_TITLE, com.mingyuechunqiu.agile.R.style.AgileDialogTheme_Transparent_Animation
        )
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let {
            it.setLayout(
                (resources.displayMetrics.widthPixels * 0.8F).toInt(), it.attributes.height
            )
        }
    }

    override fun generateInflateLayoutViewCreator(): IFragmentInflateLayoutViewCreator {
        return object :
            IFragmentInflateLayoutViewCreator.FragmentInflateLayoutViewCreatorAdapter() {

            override fun getInflateLayoutView(
                inflater: LayoutInflater, container: ViewGroup?
            ): View {
                _binding = AgileDialogFragmentCommonBinding.inflate(inflater, container, false)
                return mBinding.root
            }
        }
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        val config = arguments?.getParcelableData<Config>(EXTRA_CONFIG)
            ?: throw IllegalArgumentException("config cannot be null")
        isCancelable = config.isCancelable
        dialog?.setCanceledOnTouchOutside(config.isCancelableWithTouchOutside)
        mBinding.apply {
            if (config.titleResId != 0) {
                tvAgileCommonTitle.setText(config.titleResId)
            } else if (!config.title.isNullOrEmpty()) {
                config.title?.let {
                    tvAgileCommonTitle.text = it
                }
            } else {
                tvAgileCommonTitle.isVisible = false
            }

            if (config.messageResId != 0) {
                tvAgileCommonMsg.setText(config.messageResId)
            } else if (!config.message.isNullOrEmpty()) {
                config.message?.let {
                    tvAgileCommonMsg.text = it
                }
            } else {
                tvAgileCommonMsg.isVisible = false
            }

            tvAgileCommonNegative.setOnClickListener {
                (parentFragment as? CommonDialogFragmentCallback)?.onClickNegativeButton(config.source)
                (activity as? CommonDialogFragmentCallback)?.onClickNegativeButton(config.source)
                dismissAllowingStateLoss()
            }
            if (config.negativeButtonTextColor != 0) {
                tvAgileCommonNegative.setTextColor(config.negativeButtonTextColor)
            }
            if (config.negativeButtonTextResId != 0) {
                tvAgileCommonNegative.setText(config.negativeButtonTextResId)
            } else if (!config.negativeButtonText.isNullOrEmpty()) {
                config.negativeButtonText?.let {
                    tvAgileCommonNegative.text = it
                }
            } else {
                tvAgileCommonNegative.isVisible = false
            }

            tvAgileCommonPositive.setOnClickListener {
                (parentFragment as? CommonDialogFragmentCallback)?.onClickPositiveButton(config.source)
                (activity as? CommonDialogFragmentCallback)?.onClickPositiveButton(config.source)
                dismissAllowingStateLoss()
            }
            if (config.positiveButtonTextColor != 0) {
                tvAgileCommonPositive.setTextColor(config.positiveButtonTextColor)
            }
            if (config.positiveButtonTextResId != 0) {
                tvAgileCommonPositive.setText(config.positiveButtonTextResId)
            } else if (!config.positiveButtonText.isNullOrEmpty()) {
                config.positiveButtonText?.let {
                    tvAgileCommonPositive.text = it
                }
            } else {
                tvAgileCommonPositive.isVisible = false
            }

            if (!tvAgileCommonNegative.isVisible && !tvAgileCommonPositive.isVisible) {
                dividerAgileCommon.isVisible = false
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun releaseOnDestroyView() {
        _binding = null
    }

    override fun releaseOnDestroy() {
    }

    companion object {

        private const val TAG = "ExitDialogFragment"
        private const val EXTRA_CONFIG = "${AgileKeyPrefixConstants.KEY_BUNDLE}config"

        fun show(activity: FragmentActivity, config: Config) {
            if (activity.isFinishing || activity.isDestroyed) {
                return
            }
            newInstance(config).showSafely(activity.supportFragmentManager, TAG)
        }

        fun show(manager: FragmentManager, config: Config) {
            newInstance(config).showSafely(manager, TAG)
        }

        private fun newInstance(config: Config): AgileCommonDialogFragment {
            return AgileCommonDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_CONFIG, config)
                }
            }
        }
    }

    interface CommonDialogFragmentCallback {

        fun onClickNegativeButton(source: String)

        fun onClickPositiveButton(source: String)
    }

    class Config(val source: String) : Parcelable {

        @ColorInt
        var titleTextColor: Int = Color.TRANSPARENT
            private set

        @StringRes
        var titleResId: Int = 0
            private set
        var title: String? = null
            private set

        @ColorInt
        var messageTextColor: Int = Color.TRANSPARENT
            private set

        @StringRes
        var messageResId: Int = 0
            private set
        var message: String? = null
            private set

        @ColorInt
        var negativeButtonTextColor: Int = Color.TRANSPARENT
            private set

        @StringRes
        var negativeButtonTextResId: Int = 0
            private set
        var negativeButtonText: String? = null
            private set

        @ColorInt
        var positiveButtonTextColor: Int = Color.TRANSPARENT
            private set

        @StringRes
        var positiveButtonTextResId: Int = 0
            private set
        var positiveButtonText: String? = null
            private set

        var isCancelable: Boolean = true
            private set
        //当该属性为true时，其会覆盖isCancelable效果，但是为false时，isCancelable为true可阻止返回键生效
        var isCancelableWithTouchOutside: Boolean = true
            private set

        constructor(parcel: Parcel) : this(parcel.readString() ?: "unknown") {
            titleTextColor = parcel.readInt()
            titleResId = parcel.readInt()
            title = parcel.readString()
            messageTextColor = parcel.readInt()
            messageResId = parcel.readInt()
            message = parcel.readString()
            negativeButtonTextColor = parcel.readInt()
            negativeButtonTextResId = parcel.readInt()
            negativeButtonText = parcel.readString()
            positiveButtonTextColor = parcel.readInt()
            positiveButtonTextResId = parcel.readInt()
            positiveButtonText = parcel.readString()
            isCancelable = parcel.readInt() == 1
            isCancelableWithTouchOutside = parcel.readInt() == 1
        }

        fun setTitleTextColor(@ColorInt color: Int): Config {
            this.titleTextColor = color
            return this
        }

        fun setTitle(@StringRes resId: Int): Config {
            this.titleResId = resId
            return this
        }

        fun setTitle(title: String): Config {
            this.title = title
            return this
        }

        fun setMsgTextColor(@ColorInt color: Int): Config {
            this.messageTextColor = color
            return this
        }

        fun setMsg(@StringRes resId: Int): Config {
            this.messageResId = resId
            return this
        }

        fun setMsg(msg: String): Config {
            this.message = msg
            return this
        }

        fun setNegativeButtonTextColor(@ColorInt color: Int): Config {
            this.negativeButtonTextColor = color
            return this
        }

        fun setNegativeButtonText(resId: Int): Config {
            this.negativeButtonTextResId = resId
            return this
        }

        fun setNegativeButtonText(text: String): Config {
            this.negativeButtonText = text
            return this
        }

        fun setPositiveButtonTextColor(@ColorInt color: Int): Config {
            this.positiveButtonTextColor = color
            return this
        }

        fun setPositiveButtonText(resId: Int): Config {
            this.positiveButtonTextResId = resId
            return this
        }

        fun setPositiveButtonText(text: String): Config {
            this.positiveButtonText = text
            return this
        }

        fun setCancelable(isCancelable: Boolean): Config {
            this.isCancelable = isCancelable
            return this
        }

        fun setCancelableWithTouchOutside(isCancelableWithTouchOutside: Boolean): Config {
            this.isCancelableWithTouchOutside = isCancelableWithTouchOutside
            return this
        }

        class Builder(source: String) {

            private val mConfig = Config(source)

            fun build(): Config {
                return mConfig
            }

            fun setTitle(@StringRes resId: Int): Builder {
                mConfig.setTitle(resId)
                return this
            }

            fun setTitle(title: String): Builder {
                mConfig.setTitle(title)
                return this
            }

            fun setMsg(@StringRes resId: Int): Builder {
                mConfig.setMsg(resId)
                return this
            }

            fun setMsg(msg: String): Builder {
                mConfig.setMsg(msg)
                return this
            }

            fun setNegativeButtonTextColor(@ColorInt color: Int): Builder {
                mConfig.setNegativeButtonTextColor(color)
                return this
            }

            fun setNegativeButtonText(@StringRes resId: Int): Builder {
                mConfig.setNegativeButtonText(resId)
                return this
            }

            fun setNegativeButtonText(text: String): Builder {
                mConfig.setNegativeButtonText(text)
                return this
            }

            fun setPositiveButtonTextColor(@ColorInt color: Int): Builder {
                mConfig.setPositiveButtonTextColor(color)
                return this
            }

            fun setPositiveButtonText(@StringRes resId: Int): Builder {
                mConfig.setPositiveButtonText(resId)
                return this
            }

            fun setPositiveButtonText(text: String): Builder {
                mConfig.setPositiveButtonText(text)
                return this
            }

            fun setCancelable(isCancelable: Boolean): Builder {
                mConfig.setCancelable(isCancelable)
                return this
            }

            fun setCancelableWithTouchOutside(isCancelableWithTouchOutside: Boolean): Builder {
                mConfig.setCancelableWithTouchOutside(isCancelableWithTouchOutside)
                return this
            }
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(source)
            parcel.writeInt(titleResId)
            parcel.writeString(title)
            parcel.writeInt(messageResId)
            parcel.writeString(message)
            parcel.writeInt(negativeButtonTextColor)
            parcel.writeInt(negativeButtonTextResId)
            parcel.writeString(negativeButtonText)
            parcel.writeInt(positiveButtonTextColor)
            parcel.writeInt(positiveButtonTextResId)
            parcel.writeString(positiveButtonText)
            parcel.writeInt(if (isCancelable) 1 else 0)
            parcel.writeInt(if (isCancelableWithTouchOutside) 1 else 0)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Config> {
            override fun createFromParcel(parcel: Parcel): Config {
                return Config(parcel)
            }

            override fun newArray(size: Int): Array<Config?> {
                return arrayOfNulls(size)
            }
        }
    }
}