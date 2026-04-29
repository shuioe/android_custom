package com.xiaosi.customtools.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xiaosi.customtools.R;
import com.xiaosi.customtools.utils.Logger;

/**
 * 圆环进度条
 */
public class CircularProgressBar extends View {

    private final static String TAG = CircularProgressBar.class.getSimpleName();

    // 属性
    private int circleWidth;      // 圆环宽度
    private int backgroundColor;  // 背景颜色
    private int progressColor;    // 进度颜色
    private int maxProgress;      // 最大进度
    private int currentProgress;  // 当前进度
    private int startAngle;       // 起始角度

    // 默认属性值
    private static final int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#E0E0E0");
    private static final int DEFAULT_PROGRESS_COLOR = Color.parseColor("#4CAF50");
    private static final int DEFAULT_CIRCLE_WIDTH = 20;
    private static final int DEFAULT_MAX_PROGRESS = 100;
    private static final int DEFAULT_START_ANGLE = -90; // 12点方向

    //画笔
    private Paint backgroundPaint;
    private Paint progressPaint;

    // 绘制区域
    private RectF rectF;

    public CircularProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    public CircularProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircularProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CircularProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (context == null) {
            Logger.info(TAG, "init param is null");
            return;
        }
        // 初始化默认值
        circleWidth = DEFAULT_CIRCLE_WIDTH;
        backgroundColor = DEFAULT_BACKGROUND_COLOR;
        progressColor = DEFAULT_PROGRESS_COLOR;
        maxProgress = DEFAULT_MAX_PROGRESS;
        currentProgress = 0;
        startAngle = DEFAULT_START_ANGLE;

        rectF = new RectF();

        if (attributeSet != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircularProgressBar);
            circleWidth = typedArray.getDimensionPixelSize(
                    R.styleable.CircularProgressBar_circleWidth, DEFAULT_CIRCLE_WIDTH);
            backgroundColor = typedArray.getColor(
                    R.styleable.CircularProgressBar_backgroundColor, DEFAULT_BACKGROUND_COLOR);
            progressColor = typedArray.getColor(
                    R.styleable.CircularProgressBar_progressColor, DEFAULT_PROGRESS_COLOR);
            maxProgress = typedArray.getInteger(
                    R.styleable.CircularProgressBar_maxProgress, DEFAULT_MAX_PROGRESS);
            currentProgress = typedArray.getInteger(
                    R.styleable.CircularProgressBar_progress, 0);
            typedArray.recycle();
        }

        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(circleWidth);
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStrokeCap(Paint.Cap.ROUND);

        // 初始化进度画笔
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(circleWidth);
        progressPaint.setColor(progressColor);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = getSuggestedMinimumWidth() + getPaddingLeft() + getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getSuggestedMinimumHeight() + getPaddingTop() + getPaddingBottom();
        }

        int size = Math.min(width, height);
        setMeasuredDimension(size, size);

        // 确保rectF不为null
        if (rectF == null) {
            rectF = new RectF();
        }

        // 计算绘制区域，考虑padding
        int padding = circleWidth / 2;
        float left = padding + getPaddingLeft();
        float top = padding + getPaddingTop();
        float right = size - padding - getPaddingRight();
        float bottom = size - padding - getPaddingBottom();
        rectF.set(left, top, right, bottom);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        // 绘制背景圆环
        canvas.drawArc(rectF, 0, 360, false, backgroundPaint);

        // 绘制进度圆环
        if (currentProgress > 0) {
            float sweepAngle = 360f * currentProgress / maxProgress;
            canvas.drawArc(rectF, startAngle, sweepAngle, false, progressPaint);
        }

        // 可选的：绘制进度文本
        // drawProgressText(canvas);
    }

    public void setProgress(int progress) {
        if (progress >= 100) {
            this.currentProgress = 100;
        } else {
            this.currentProgress = Math.min(Math.max(progress, 0), maxProgress);
        }
        invalidate(); // 触发重绘
    }

    public int getProgress() {
        return currentProgress;
    }

    /**
     * 设置最大进度
     * @param maxProgress 最大进度
     */
    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        invalidate();
    }

    /**
     * 获取最大进度
     * @return 最大进度
     */
    public int getMaxProgress() {
        return maxProgress;
    }

    /**
     * 设置圆环宽度
     * @param width 宽度（像素）
     */
    public void setCircleWidth(int width) {
        this.circleWidth = width;
        backgroundPaint.setStrokeWidth(width);
        progressPaint.setStrokeWidth(width);
        requestLayout(); // 需要重新测量
    }

    /**
     * 设置背景颜色
     * @param color 颜色
     */
    public void setBackgroundColor(int color) {
        this.backgroundColor = color;
        backgroundPaint.setColor(color);
        invalidate();
    }

    /**
     * 设置进度颜色
     * @param color 颜色
     */
    public void setProgressColor(int color) {
        this.progressColor = color;
        progressPaint.setColor(color);
        invalidate();
    }

    /**
     * 设置起始角度
     * @param angle 角度（0-360）
     */
    public void setStartAngle(int angle) {
        this.startAngle = angle;
        invalidate();
    }

    // 可选：绘制进度文本的方法
    private void drawProgressText(Canvas canvas) {
        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40);
        textPaint.setTextAlign(Paint.Align.CENTER);

        String text = currentProgress + "%";
        float x = getWidth() / 2f;
        float y = getHeight() / 2f - (textPaint.ascent() + textPaint.descent()) / 2;

        canvas.drawText(text, x, y, textPaint);
    }
}
