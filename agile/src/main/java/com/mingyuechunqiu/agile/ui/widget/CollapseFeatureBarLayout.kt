package com.mingyuechunqiu.agile.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.setPadding
import com.mingyuechunqiu.agile.R
import com.mingyuechunqiu.agile.feature.helper.ui.common.ScreenHelper
import kotlin.math.max

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2022/8/3 23:15
 *      Desc:       展开收缩按钮集控件
 *                  继承自FrameLayout
 *      Version:    1.0
 * </pre>
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class CollapseFeatureBarLayout<T> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    var mainButtonBackground: Drawable? = null
    var mainButtonSize: Int
    var mainIconDrawable: Drawable? = null
    var mainIconSize: Int
    var mainHorizontalRatio: Float
    var mainVerticalRatio: Float
    var featureBarItemPadding: Int
    var featureBarItemIconSize: Int
    var featureBarItemTextSize: Float
    var featureBarItemInnerPadding: Int
    var featureBarItemIconPadding: Int

    @ColorInt
    var featureBarItemTextColor: Int

    var featureBarItemList: List<FeatureBarItem<T>> = ArrayList()
        set(value) {
            field = value
            mFeatureBarViews.clear()
            removeAllViews()
            addFeatureComponents(value)
        }
    var collapseFeatureBarLayoutListener: CollapseFeatureBarLayoutListener<T>? = null

    private val mFeatureBarViews: MutableList<View> = ArrayList()
    private var isInExpand = false//当前是否处于展开状态

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CollapseFeatureBarLayout)
        mainButtonBackground =
            a.getDrawable(R.styleable.CollapseFeatureBarLayout_cfbl_main_button_background)
        mainButtonSize = a.getDimensionPixelSize(
            R.styleable.CollapseFeatureBarLayout_cfbl_main_button_size,
            ScreenHelper.getPxFromDp(resources, DEFAULT_MAIN_BUTTON_SIZE).toInt()
        )
        mainIconDrawable =
            a.getDrawable(R.styleable.CollapseFeatureBarLayout_cfbl_main_icon_drawable)
        mainIconSize = a.getDimensionPixelSize(
            R.styleable.CollapseFeatureBarLayout_cfbl_main_icon_size,
            ScreenHelper.getPxFromDp(resources, DEFAULT_MAIN_ICON_SIZE).toInt()
        )
        mainHorizontalRatio =
            a.getFloat(
                R.styleable.CollapseFeatureBarLayout_cfbl_main_horizontal_ratio,
                DEFAULT_MAIN_HORIZONTAL_RATIO
            )
        mainVerticalRatio =
            a.getFloat(
                R.styleable.CollapseFeatureBarLayout_cfbl_main_vertical_ratio,
                DEFAULT_MAIN_VERTICAL_RATIO
            )
        featureBarItemPadding = a.getDimensionPixelSize(
            R.styleable.CollapseFeatureBarLayout_cfbl_feature_bar_item_padding,
            ScreenHelper.getPxFromDp(resources, DEFAULT_FEATURE_BAR_ITEM_PADDING).toInt()
        )
        featureBarItemIconSize = a.getDimensionPixelSize(
            R.styleable.CollapseFeatureBarLayout_cfbl_feature_bar_item_icon_size,
            ScreenHelper.getPxFromDp(resources, DEFAULT_FEATURE_BAR_ITEM_ICON_SIZE).toInt()
        )
        featureBarItemTextSize = a.getDimension(
            R.styleable.CollapseFeatureBarLayout_cfbl_feature_bar_item_text_size,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                DEFAULT_FEATURE_BAR_ITEM_TEXT_SIZE,
                resources.displayMetrics
            )
        )
        featureBarItemTextColor =
            a.getColor(
                R.styleable.CollapseFeatureBarLayout_cfbl_feature_bar_item_text_color,
                Color.BLACK
            )
        featureBarItemInnerPadding = a.getDimensionPixelSize(
            R.styleable.CollapseFeatureBarLayout_cfbl_feature_bar_item_inner_padding,
            ScreenHelper.getPxFromDp(resources, DEFAULT_FEATURE_BAR_ITEM_INNER_PADDING).toInt()
        )
        featureBarItemIconPadding = a.getDimensionPixelSize(
            R.styleable.CollapseFeatureBarLayout_cfbl_feature_bar_item_icon_padding,
            featureBarItemIconSize / 4
        )
        a.recycle()
        //以便Item阴影可以正常显示，不会被切割
        clipChildren = false
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val vMain = getMainButton()
        //根据用户语言显示方向，计算主按钮左边起始位置
        val mainStart: Int =
            if (resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL) (width * (1 - mainHorizontalRatio)).toInt() else {
                val tempStart = (width * mainHorizontalRatio).toInt()
                if (tempStart + vMain.width > width) {
                    (width * mainHorizontalRatio - vMain.width).toInt()
                } else {
                    tempStart
                }
            }
        val tempTop = (height * mainVerticalRatio).toInt()
        val mainTop = if (tempTop + vMain.height > height) {
            (height * mainVerticalRatio - vMain.height).toInt()
        } else {
            tempTop
        }
        val mainRight = mainStart + vMain.width
        vMain.layout(mainStart, mainTop, mainRight, mainTop + vMain.height)
        mFeatureBarViews.forEach {
            val itemStart =
                if (resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL) mainStart else mainRight - it.width
            val itemRight =
                if (resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL) mainStart + it.width else mainRight
            it.layout(itemStart, mainTop, itemRight, mainTop + it.height)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                return isInExpand
            }
            MotionEvent.ACTION_UP -> {
                if (isInExpand) {
                    collapseFeatureBarLayoutListener?.onClickOutside()
                    return true
                }
            }
        }
        return super.onTouchEvent(event)
    }

    fun initialize(
        featureBarItemList: List<FeatureBarItem<T>>,
        listener: CollapseFeatureBarLayoutListener<T>
    ) {
        addFeatureComponents(featureBarItemList)
        collapseFeatureBarLayoutListener = listener
    }

    fun expand() {
        if (isInExpand) {
            return
        }
        mFeatureBarViews.forEach {
            it.visibility = View.VISIBLE
        }
        mFeatureBarViews.forEachIndexed { index, view ->
            view.visibility = View.VISIBLE
            view.animate().translationY(
                -(view.height * (mFeatureBarViews.size - index)).toFloat()
            )
                .alpha(1.0F)
                .setInterpolator(OvershootInterpolator())
                .setDuration(DEFAULT_ANIMATION_DURATION)
                .start()
        }
        getMainButton().animate()
            .rotation(45F)
            .setInterpolator(OvershootInterpolator())
            .setDuration(DEFAULT_ANIMATION_DURATION).start()
        isInExpand = true
    }

    fun collapse() {
        if (!isInExpand) {
            return
        }
        mFeatureBarViews.forEach {
            it.animate().translationY(0F)
                .alpha(0F)
                .setInterpolator(OvershootInterpolator())
                .setDuration(DEFAULT_ANIMATION_DURATION)
                .start()
        }
        getMainButton().animate()
            .rotation(0F)
            .setInterpolator(OvershootInterpolator())
            .setDuration(DEFAULT_ANIMATION_DURATION)
            .withEndAction {
                mFeatureBarViews.forEach {
                    it.visibility = View.INVISIBLE
                }
                isInExpand = false
            }
            .start()
    }

    fun isInExpand() = isInExpand

    private fun addFeatureComponents(featureBarItemList: List<FeatureBarItem<T>>) {
        featureBarItemList.forEach {
            val addView = createFeatureBarItemViewGroup(it)
            mFeatureBarViews.add(addView)
            addView(addView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            addView.visibility = INVISIBLE
        }
        addMainButton(context)
    }

    private fun createFeatureBarItemViewGroup(item: FeatureBarItem<T>): ViewGroup {
        return LinearLayout(context).apply {
            gravity = Gravity.CENTER_VERTICAL
            clipToPadding = false
            addView(AppCompatTextView(context).apply {
                background =
                    ContextCompat.getDrawable(context, R.drawable.agile_shape_round_corners_8_white)
                val horizontalPadding = ScreenHelper.getPxFromDp(context.resources, 24F).toInt()
                val verticalPadding = ScreenHelper.getPxFromDp(context.resources, 6F).toInt()
                setPaddingRelative(
                    horizontalPadding,
                    verticalPadding,
                    horizontalPadding,
                    verticalPadding
                )
                elevation = ScreenHelper.getPxFromDp(context.resources, 6F)
                text = item.name
                setTextSize(TypedValue.COMPLEX_UNIT_PX, featureBarItemTextSize)
                setTextColor(featureBarItemTextColor)
            })
            val ivIcon = AppCompatImageView(context).apply {
                elevation = ScreenHelper.getPxFromDp(context.resources, 6F)
                if (item.iconBgResId != 0) {
                    background = ContextCompat.getDrawable(context, item.iconBgResId)
                }
                setPadding(featureBarItemIconPadding)
                if (item.iconTint == 0) {
                    setImageResource(item.iconResId)
                } else {
                    ContextCompat.getDrawable(context, item.iconResId)?.let {
                        val wrapDrawable = DrawableCompat.wrap(it)
                        wrapDrawable.setTint(item.iconTint)
                        setImageDrawable(wrapDrawable)
                    }
                }
            }
            addView(ivIcon, featureBarItemIconSize, featureBarItemIconSize)
            val iconPadding = max(0, (mainButtonSize - featureBarItemIconSize) / 2)
            (ivIcon.layoutParams as LinearLayout.LayoutParams).apply {
                //保持图标和主按钮垂直居中对齐
                marginStart = featureBarItemInnerPadding + iconPadding
                marginEnd = iconPadding
            }
            setPadding(0, 0, 0, featureBarItemPadding)
            setOnClickListener {
                collapseFeatureBarLayoutListener?.onClickFeatureBarItem(item)
                //延迟折叠，防止动画和操作重叠，影响展示效果
                postDelayed({ collapse() }, 200L)
            }
        }
    }

    private fun addMainButton(context: Context) {
        addView(FrameLayout(context).apply {
            elevation = ScreenHelper.getPxFromDp(resources, 6F)
            foregroundGravity
            background = mainButtonBackground
            val ivIcon = AppCompatImageView(context).apply {
                setImageDrawable(mainIconDrawable)
            }
            addView(ivIcon, mainIconSize, mainIconSize)
            (ivIcon.layoutParams as LayoutParams).gravity = Gravity.CENTER
            setOnClickListener {
                collapseFeatureBarLayoutListener?.onClickMainButton(isInExpand)
            }
        }, mainButtonSize, mainButtonSize)
    }

    private fun getMainButton(): View {
        return getChildAt(childCount - 1)
    }

    companion object {

        private const val DEFAULT_MAIN_BUTTON_SIZE = 50F//单位DP
        private const val DEFAULT_MAIN_ICON_SIZE = 20F//单位DP
        private const val DEFAULT_MAIN_HORIZONTAL_RATIO = 0.94F
        private const val DEFAULT_MAIN_VERTICAL_RATIO = 0.88F
        private const val DEFAULT_FEATURE_BAR_ITEM_PADDING = 20F // 单位DP
        private const val DEFAULT_FEATURE_BAR_ITEM_ICON_SIZE = 24F//单位DP
        private const val DEFAULT_FEATURE_BAR_ITEM_TEXT_SIZE = 14F//单位SP
        private const val DEFAULT_FEATURE_BAR_ITEM_INNER_PADDING = 20F//单位DP
        private const val DEFAULT_ANIMATION_DURATION = 380L//默认动画时间
    }

    data class FeatureBarItem<T>(
        @DrawableRes val iconResId: Int,
        val name: String,
        @ColorInt val iconTint: Int = 0,
        @DrawableRes val iconBgResId: Int = 0,
        val data: T
    )

    interface CollapseFeatureBarLayoutListener<T> {

        fun onClickMainButton(isInExpand: Boolean)

        fun onClickOutside()

        fun onClickFeatureBarItem(item: FeatureBarItem<T>)
    }
}