package com.example.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * @author : created by ZWH
 * Date : 2019/10/9
 * email ： prentysst@163.com
 * QQ ： 1046352167
 * 柱状体
 */
public class ColumnarView extends View {

    // 控件外边距 左
    private float mNarMarginLeft = 0;
    // 控件外边距 右
    private float mNarMarginRight = 0;
    // 控件外边距 上
    private float mNarMarginTop = 0;
    // 控件外边距 下
    private float mNarMarginBottom = 0;
    // 外框线宽度
    private float mNarWidth = 0;
    // 柱 外边距
    private float mNarColumnMargin = 0;
    // 柱状高度
    private float mNarColumnHeight = 0;
    // 圆角半径
    private float mNarRadius = 0;
    // 满值计算标准
    private int mNarFull = 100;
    // 文字大小
    private float mTextSize = 0;
    // 字体颜色
    private int mTextColor = 0;
    // 框的颜色
    private int mNarBackdrop = 0;
    // 柱体的颜色
    private int mNarColumnBackdrop = 0;
    // 柱状体 距离文字的高度
    private float mColumnFontTop = 0;
    // 数据
    private ArrayList<ColumnarBean> mColumnarData = new ArrayList<>();

    // 柱状图 柱
    private Paint mColumnPaint;
    // 柱状图 框
    private Paint mFramePaint;
    // 文字
    private Paint mFontPaint;


    public ColumnarView(Context context) {
        super(context);
    }

    public ColumnarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initPaint();
    }

    public ColumnarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initPaint();
    }

    /**
     * 初始化Attrs
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ColumnarViewStyle);
        mNarMarginLeft = ta.getDimension(R.styleable.ColumnarViewStyle_narMarginLeft, 0);
        mNarMarginRight = ta.getDimension(R.styleable.ColumnarViewStyle_narMarginRight, 0);
        mNarMarginTop = ta.getDimension(R.styleable.ColumnarViewStyle_narMarginTop, 0);
        mNarMarginBottom = ta.getDimension(R.styleable.ColumnarViewStyle_narMarginBottom, 0);
        mNarColumnMargin = ta.getDimension(R.styleable.ColumnarViewStyle_narColumnMargin, 0);
        mNarColumnHeight = ta.getDimension(R.styleable.ColumnarViewStyle_narColumnHeight, dp2px(8));
        mNarFull = ta.getInteger(R.styleable.ColumnarViewStyle_narFull, 100);
        mNarRadius = ta.getDimension(R.styleable.ColumnarViewStyle_narRadius, 0);
        mColumnFontTop = ta.getDimension(R.styleable.ColumnarViewStyle_narColumnFontTop, sp2px(6));
        mNarWidth = ta.getDimension(R.styleable.ColumnarViewStyle_narWidth, sp2px(1));
        mTextSize = ta.getDimension(R.styleable.ColumnarViewStyle_narTxtSize, sp2px(12));
        mTextColor = ta.getColor(R.styleable.ColumnarViewStyle_narTxtColor, Color.parseColor("#4185F4"));
        mNarBackdrop = ta.getColor(R.styleable.ColumnarViewStyle_narBackdrop, Color.parseColor("#4185F4"));
        mNarColumnBackdrop = ta.getColor(R.styleable.ColumnarViewStyle_narColumnBackdrop, Color.parseColor("#4185F4"));
        ta.recycle();

    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        // 边框
        mFramePaint = new Paint();
        mFramePaint.setAntiAlias(true);
        mFramePaint.setStyle(Paint.Style.STROKE);
        mFramePaint.setStrokeWidth(mNarWidth);
        mFramePaint.setColor(mNarBackdrop);

        // 柱状
        mColumnPaint = new Paint();
        mColumnPaint.setAntiAlias(true);
        mColumnPaint.setStyle(Paint.Style.FILL);
        mColumnPaint.setColor(mNarColumnBackdrop);

        mFontPaint = new Paint();
        mFontPaint.setAntiAlias(true);
        mFontPaint.setStyle(Paint.Style.FILL);
        mFontPaint.setTextSize(mTextSize);
        mFontPaint.setColor(mTextColor);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        for (ColumnarBean bean : mColumnarData) {
            if (bean.getNarCount() > mNarFull) {
                mNarFull = bean.getNarCount();
            }
        }
        // 总行数
        int row = mColumnarData.size();
        // 矩形1
        mFramePaint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < row; i++) {
            float left = mNarMarginLeft;
            // 计算公式：i x 字体高度 + 字体高度 + i x 柱距离文字的高度 + 柱距离文字的高度 + i x 控件上边距 + 控件上边距 + i x 控件下边距 + 控件下边距 + i x 柱的高度 + i x (柱的外边距 x 2) PS：因为柱外面有矩形框包围 包含上下边距，故x2
            float top = i * mTextSize + mTextSize + i * mColumnFontTop + mColumnFontTop + i * mNarMarginTop + mNarMarginTop + i * mNarMarginBottom + i * mNarColumnHeight + i * (mNarColumnMargin * 2);
            float right = width - mNarMarginRight;
            float bottom = i * mTextSize + mTextSize + i * mColumnFontTop + mColumnFontTop + i * mNarMarginTop + mNarMarginTop
                          + i * mNarMarginBottom + i * mNarColumnHeight + mNarColumnHeight + i * (mNarColumnMargin * 2) + mNarColumnMargin * 2;
            Log.d("TAG", "left=" + left + ",top=" + top + ",right=" + right + ",bottom=" + bottom);
            RectF oval1 = new RectF(left, top, right, bottom);// 设置个新的长方形
            canvas.drawRoundRect(oval1, mNarRadius, mNarRadius, mFramePaint);//第二个参数是x半径，第三个参数是y半径

            float left2 = mNarMarginLeft + mNarColumnMargin;
            float top2 = i * mTextSize + mTextSize + i * mColumnFontTop + mColumnFontTop + i * mNarMarginTop + mNarMarginTop + i * mNarMarginBottom + i * mNarColumnHeight + i * (mNarColumnMargin * 2) + mNarColumnMargin;
            float right2 = width - mNarMarginRight - mNarColumnMargin;
            float mProgress = (right2 - left2) / mNarFull;
            if (mProgress < 0) {
                mProgress = Math.abs(mProgress);
            }
            int narCount = mColumnarData.get(i).getNarCount();
            if (narCount < 0) narCount = 0;

            if (narCount > mNarFull) narCount = mNarFull;

            right2 = left2 + narCount * mProgress;
            float bottom2 = i * mTextSize + mTextSize + i * mColumnFontTop + mColumnFontTop + i * mNarMarginTop + mNarMarginTop
                           + i * mNarMarginBottom + i * mNarColumnHeight + mNarColumnHeight + i * (mNarColumnMargin * 2) + mNarColumnMargin;
            RectF oval2 = new RectF(left2, top2, right2, bottom2);// 设置个新的长方形
            canvas.drawRoundRect(oval2, mNarRadius, mNarRadius, mColumnPaint);//第二个参数是x半径，第三个参数是y半径

            float fontX = mNarMarginLeft;
            float fontY = top - mColumnFontTop;
            canvas.drawText(mColumnarData.get(i).getNarContent(), fontX, fontY, mFontPaint);

        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 这里宽度没有进行计算，如果需要自行计算宽度
        int width = widthSize;
        int height = heightSize;
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                // 计算公式： 数据条数 x （字体高度 + 柱距离文字的高度 + 控件Top + 控件Bottom + 2 * 柱内边距 + 柱的高度）
                height = (int) (mColumnarData.size() * (mTextSize + mColumnFontTop + mNarMarginTop + mNarMarginBottom + 2 * mNarColumnMargin + mNarColumnHeight));
                break;
        }
        setMeasuredDimension(width, height);
    }

    /**
     * 获取一行高度
     */
    private float getRowHeight() {
        return mTextSize;
    }


    /**
     * 设置最大值
     */
    public ColumnarView setNarFull(int narFull) {
        mNarFull = 100;
        if (narFull > 0) {
            mNarFull = narFull;
        }
        return this;
    }

    /**
     * 返回最大值
     */
    public int getNarFull() {
        return mNarFull;
    }

    /**
     * 设置新数据
     *
     * @param list
     */
    public ColumnarView setNewData(ArrayList<ColumnarBean> list) {
        mColumnarData.clear();
        if (list != null) {
            mColumnarData.addAll(list);
        }
        return this;
    }

    public void build() {
        postInvalidate();
    }

    /**
     * dp转px
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private int dp2px(float dp) {
        float density = getResources().getDisplayMetrics().scaledDensity;
        return (int) (dp * density + 0.5);
    }

    private int sp2px(float sp) {
        float density = getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * density + 0.5);
    }


}
