package com.mingyuechunqiu.agilemvpframe.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.ListPopupWindow;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;

import com.mingyuechunqiu.agilemvpframe.R;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/06/19
 *     desc   : 具有收回、下拉功能的自动提示输入框
 *              继承自AppCompatEditText
 *     version: 1.0
 * </pre>
 */
public class PullDownEditText extends AppCompatEditText {

    private static final int DEFAULT_PULL_DOWN_RES_ID = R.drawable.pull_down;
    private static final int DEFAULT_RETRACT_RES_ID = R.drawable.retract;
    private static final int DEFAULT_CLEAR_RES_ID = R.drawable.close;
    //图标按钮的间距、宽度、距离右边边距
    private final int DEFAULT_BUTTON_PADDING =
            getResources().getDimensionPixelSize(R.dimen.dp_5);
    private final int DEFAULT_BUTTON_WIDTH =
            getResources().getDimensionPixelSize(R.dimen.dp_20);
    private final int DEFAULT_BUTTON_RIGHT_MARGIN = getResources().getDimensionPixelSize(R.dimen.dp_5);
    private static final int ANIMATOR_TIME = 200;//动画时间

    private ListPopupWindow lpwCompletion;

    private boolean isPullDownVisible, isClearVisible;//下拉、清除图标是否可见
    private int mPullDownResId, mRetractResId, mClearResId;//图片资源id
    private Bitmap mBpPullDown, mBpRetract, mBpClear;
    private int mBtnPadding, mTextPadding;//防止文字与图标重叠
    private int mBtnWidth;
    private int mBtnRightMargin;//最右边按钮距离边框距离
    private boolean isPullDown, isClearShown;//记录当前是否已经下拉还是收回，清除按钮是否可见
    //出现和消失动画
    private ValueAnimator mGoneAnimator;
    private ValueAnimator mVisibleAnimator;
    private Paint mPaint;
    private OnClickPullDownBtnListener mListener;
    private boolean isOriginalTouch;//记录原始的下拉收回状态，因为消失事件会在触摸事件之前触发
    private ListAdapter mAdapter;
    private int mLines, mHeight;//记录弹窗显示行数，弹窗高度

    public PullDownEditText(Context context) {
        this(context, null);
    }

    public PullDownEditText(Context context, AttributeSet attrs) {
        //不能调用this(context, attrs, 0)，会造成样式问题
        super(context, attrs);
        init(context, attrs);
    }

    public PullDownEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //设置右内边距, 防止清除按钮和文字重叠
        setPadding(getPaddingLeft(), getPaddingTop(), mTextPadding, getPaddingBottom());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPullDown(canvas);
        drawClearBtn(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //如果点击的时候，里面已经有文字了，则显示清除图标
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!isClearShown && length() > 0) {
                isClearShown = true;
                startVisibleAnimator();
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {

            boolean pullDownTouched =
                    (getWidth() - mBtnPadding - mBtnRightMargin - mBtnWidth < event.getX())
                            && (event.getX() < getWidth() - mBtnPadding - mBtnRightMargin)
                            && isFocused();
            boolean clearTouched =
                    (getWidth() - mBtnPadding * 3 - mBtnRightMargin - mBtnWidth * 2 < event.getX())
                            && (event.getX() < getWidth() - mBtnPadding * 3 - mBtnRightMargin - mBtnWidth)
                            && isFocused();

            if (pullDownTouched) {
                //如果没有数据则不显示收回下拉图标，并且收回下拉图标位置显示的是清除图标
                if (mAdapter == null || mAdapter.getCount() == 0) {
                    setText("");
                    return true;
                }
                //因为OnDismissListener会在onTouch之前调用，所以如果用户是点击收回图标，
                // 要判断两者标志是否一致，如果不一致，则要重置isPullDown
                if (isPullDown != isOriginalTouch) {
                    isPullDown = isOriginalTouch;
                }
                if (isPullDown) {
                    isPullDown = false;
                    isOriginalTouch = false;
                    lpwCompletion.dismiss();
                    if (mListener != null) {
                        mListener.onRetract();
                    }
                } else {
                    isPullDown = true;
                    isOriginalTouch = true;
                    lpwCompletion.show();
                    if (mListener != null) {
                        mListener.onPullDown();
                    }
                }
                invalidate();
                return true;
            } else if (clearTouched) {
                setText("");
                if (mListener != null) {
                    mListener.onClickClear();
                }
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (text.length() > 0 && isFocused()) {
            if (!isClearShown) {
                isClearShown = true;
                startVisibleAnimator();
            }
        } else {
            if (isClearShown) {
                isClearShown = false;
                startGoneAnimator();
            }
        }
    }


    public OnClickPullDownBtnListener getOnClickPullDownBtnListener() {
        return mListener;
    }

    public void setOnClickPullDownBtnListener(OnClickPullDownBtnListener mListener) {
        this.mListener = mListener;
    }

    public void setCompletionAdapter(ListAdapter adapter) {
        mAdapter = adapter;
        lpwCompletion.setAdapter(adapter);
    }

    public ListAdapter getCompletionAdapter() {
        return mAdapter;
    }

    public void showCompletionList() {
        if (lpwCompletion.isShowing()) {
            return;
        }
        lpwCompletion.show();
    }

    public void dismissCompletionList() {
        lpwCompletion.dismiss();
    }

    /**
     * 设置弹窗高度
     *
     * @param lines  显示多少行
     * @param height 弹窗高度
     */
    public void setCompletionListHeight(int lines, int height) {
        if (lines <= 0 || height <= 0) {
            return;
        }
        mLines = lines;
        mHeight = height;
        //弹窗显示高度以4行为准
        if (mAdapter.getCount() < lines) {
            lpwCompletion.setHeight(height / lines * mAdapter.getCount());
        } else {
            lpwCompletion.setHeight(height);
        }
    }

    public int getCompletionListHeight() {
        return lpwCompletion.getHeight();
    }

    public void notifyDataChanged() {
        ((BaseAdapter) mAdapter).notifyDataSetChanged();
        setCompletionListHeight(mLines, mHeight);
        lpwCompletion.dismiss();
    }

    /**
     * 设置图标为下拉，补全列表收回
     */
    public void setPullDown() {
        isPullDown = false;
        isOriginalTouch = false;
        invalidate();
    }

    /**
     * 绘制清除按钮
     *
     * @param canvas 画布
     */
    private void drawClearBtn(Canvas canvas) {
        //绘制按钮显示与消失效果
        if (isClearShown) {
            if (mVisibleAnimator.isRunning()) {
                float scale = (float) mVisibleAnimator.getAnimatedValue();
                drawClearBtn(scale, canvas);
            } else {
                drawClearBtn(1, canvas);
            }
        } else {
            if (mGoneAnimator.isRunning()) {
                float scale = (float) mGoneAnimator.getAnimatedValue();
                drawClearBtn(scale, canvas);
            }
        }
        invalidate();
    }

    /**
     * 初始化相关资源
     *
     * @param context 上下文
     * @param attrs   属性
     */
    private void init(Context context, AttributeSet attrs) {
        //抗锯齿和位图滤波
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PullDownEditText);
        if (a != null) {
            isPullDownVisible = a.getBoolean(R.styleable.PullDownEditText_pullDownIsVisible, false);
            isClearVisible = a.getBoolean(R.styleable.PullDownEditText_clearBtnIsVisible, false);
            mPullDownResId = a.getResourceId(R.styleable.PullDownEditText_pullDownDrawable, DEFAULT_PULL_DOWN_RES_ID);
            mRetractResId = a.getResourceId(R.styleable.PullDownEditText_retractDrawable, DEFAULT_RETRACT_RES_ID);
            mClearResId = a.getResourceId(R.styleable.PullDownEditText_clearBtnDrawable, DEFAULT_CLEAR_RES_ID);
            mBtnPadding = a.getDimensionPixelSize(R.styleable.PullDownEditText_buttonPadding, DEFAULT_BUTTON_PADDING);
            mBtnWidth = a.getDimensionPixelSize(R.styleable.PullDownEditText_buttonWidth, DEFAULT_BUTTON_WIDTH);
            mBtnRightMargin = a.getDimensionPixelSize(R.styleable.PullDownEditText_buttonRightMargin, DEFAULT_BUTTON_RIGHT_MARGIN);
            if (isPullDownVisible) {
                mBpPullDown = BitmapFactory.decodeResource(getResources(), mPullDownResId);
                mBpRetract = BitmapFactory.decodeResource(getResources(), mRetractResId);
            }
            if (isClearVisible) {
                mBpClear = BitmapFactory.decodeResource(getResources(), mClearResId);
            }
            a.recycle();
        }
        //给文字设置一个padding，避免文字和按钮重叠了
        mTextPadding = mBtnPadding * 4 + mBtnWidth * 2;
        //按钮出现和消失的动画
        mGoneAnimator = ValueAnimator.ofFloat(1f, 0f).setDuration(ANIMATOR_TIME);
        mVisibleAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(ANIMATOR_TIME);
        initCompletionList();
    }

    /**
     * 初始化补全列表
     */
    private void initCompletionList() {
        lpwCompletion = new ListPopupWindow(getContext());
        lpwCompletion.setAnchorView(this);
        lpwCompletion.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isPullDown = false;
                invalidate();
            }
        });
    }

    /**
     * 绘制下拉收回图标按钮
     *
     * @param canvas 画布
     */
    private void drawPullDown(Canvas canvas) {
        if (!isPullDownVisible) {
            return;
        }
        //如果列表没有数据，则不绘制图形
        if (mAdapter.getCount() == 0) {
            return;
        }
        int right = getWidth() + getScrollX() - mBtnPadding - mBtnRightMargin;
        int left = getWidth() + getScrollX() - mBtnPadding - mBtnRightMargin - mBtnWidth;
        int top = getHeight() - mBtnWidth - mBtnPadding;
        int bottom = top + mBtnWidth / 2;
        Rect rect = new Rect(left, top, right, bottom);
        if (isPullDown) {
            canvas.drawBitmap(mBpRetract, null, rect, mPaint);
        } else {
            canvas.drawBitmap(mBpPullDown, null, rect, mPaint);
        }
    }

    /**
     * 绘制清除按钮
     *
     * @param scale  根据动画进度显示绘制效果
     * @param canvas 画布
     */
    private void drawClearBtn(float scale, Canvas canvas) {
        if (!isClearVisible) {
            return;
        }
        //如果有收回下拉图标，则按钮绘制在左边，否则绘制在最后面
        int left, right, top, bottom;
        if (mAdapter.getCount() == 0) {
            right = (int) (getWidth() + getScrollX() - mBtnPadding - mBtnRightMargin -
                    mBtnWidth * (1f - scale) / 2f);
            left = (int) (getWidth() + getScrollX() - mBtnPadding - mBtnRightMargin -
                    mBtnWidth * (scale + (1f - scale) / 2f));
            top = (int) ((getHeight() - mBtnWidth * scale) / 2);
            bottom = (int) (top + mBtnWidth * scale);
        } else {
            right = (int) (getWidth() + getScrollX() - mBtnPadding * 3 - mBtnRightMargin -
                    mBtnWidth - mBtnWidth * (1f - scale) / 2f);
            left = (int) (getWidth() + getScrollX() - mBtnPadding * 3 - mBtnWidth - mBtnRightMargin -
                    mBtnWidth * (scale + (1f - scale) / 2f));
            top = (int) ((getHeight() - mBtnWidth * scale) / 2);
            bottom = (int) (top + mBtnWidth * scale);
        }
        Rect rect = new Rect(left, top, right, bottom);
        canvas.drawBitmap(mBpClear, null, rect, mPaint);
    }

    // 清除按钮出现时的动画效果
    private void startVisibleAnimator() {
        endAllAnimator();
        mVisibleAnimator.start();
        invalidate();
    }

    // 清除按钮消失时的动画效果
    private void startGoneAnimator() {
        endAllAnimator();
        mGoneAnimator.start();
        invalidate();
    }

    // 结束所有动画
    private void endAllAnimator() {
        mGoneAnimator.end();
        mVisibleAnimator.end();
    }

    /**
     * 监听按钮下拉收回的点击事件
     */
    public interface OnClickPullDownBtnListener {

        /**
         * 当点击收回按钮时回调
         */
        void onPullDown();

        /**
         * 当点击下拉按钮时回调
         */
        void onRetract();

        /**
         * 当点击清除按钮时回调
         */
        void onClickClear();
    }
}
