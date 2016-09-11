package com.android.loadingview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 两齿轮转动效果
 */
public class GearsTwo extends View {
	
	private float mWidth = 0f;
    private Paint mPaint, mPaintAxle;
    private Paint mPaintRing;

    private float mPadding = 0f;
    private float mWheelLength;//齿轮高度

    private int mWheelSmallSpace = 10;
    private int mWheelBigSpace = 8;

    ValueAnimator valueAnimator = null;
    float mAnimatedValue = 0f;

    float hypotenuse = 0f;
    float smallRingCenterX = 0f;
    float smallRingCenterY = 0f;
    float bigRingCenterX = 0f;
    float bigRingCenterY = 0f;
	
	public GearsTwo(Context context) {
		// super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}
	
	public GearsTwo(Context context, AttributeSet attrs) {
		// super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}

	public GearsTwo(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		initPaint();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		// 设置视图的大小即View本身的大小
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		// 获取宽
		if (getMeasuredWidth() > getHeight())
            mWidth = getMeasuredHeight();
        else
            mWidth = getMeasuredWidth();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		canvas.save();
        canvas.rotate(180, mWidth / 2, mWidth / 2);

        drawSmallRing(canvas);
        drawSmallGear(canvas);
        drawBigGear(canvas);
        drawBigRing(canvas);
        drawAxle(canvas);
        canvas.restore();
	}
	
	/**
	 * 绘制小轮
	 * @param canvas
	 */
	private void drawSmallRing(Canvas canvas) {
        hypotenuse = (float) (mWidth * Math.sqrt(2));
        smallRingCenterX = (float) ((hypotenuse / 6.f) * Math.cos(45 * Math.PI / 180f));
        smallRingCenterY = (float) ((hypotenuse / 6.f) * Math.sin(45 * Math.PI / 180f));
        mPaintRing.setStrokeWidth(dip2px(1.0f));
        canvas.drawCircle(mPadding + smallRingCenterX, smallRingCenterY + mPadding, smallRingCenterX, mPaintRing);
        mPaintRing.setStrokeWidth(dip2px(1.5f));
        canvas.drawCircle(mPadding + smallRingCenterX, smallRingCenterY + mPadding, smallRingCenterX / 2, mPaintRing);
    }
	
	/**
	 * 绘制小齿轮
	 * @param canvas
	 */
	private void drawSmallGear(Canvas canvas) {
        mPaint.setStrokeWidth(dip2px(1));
        for (int i = 0; i < 360; i = i + mWheelSmallSpace) {
            int angle = (int) (mAnimatedValue * mWheelSmallSpace + i);
            float x3 = (float) ((smallRingCenterX) * Math.cos(angle * Math.PI / 180f));
            float y3 = (float) ((smallRingCenterY) * Math.sin(angle * Math.PI / 180f));
            float x4 = (float) ((smallRingCenterX + mWheelLength) * Math.cos(angle * Math.PI / 180f));
            float y4 = (float) ((smallRingCenterY + mWheelLength) * Math.sin(angle * Math.PI / 180f));

            canvas.drawLine(mPadding + smallRingCenterX - x4,
                    smallRingCenterY + mPadding - y4,
                    smallRingCenterX + mPadding - x3,
                    smallRingCenterY + mPadding - y3,
                    mPaint);
        }
    }
	
	/**
	 * 绘制大齿轮
	 * @param canvas
	 */
	private void drawBigGear(Canvas canvas) {
        bigRingCenterX = (float) ((hypotenuse / 2.f) * Math.cos(45 * Math.PI / 180f));
        bigRingCenterY = (float) ((hypotenuse / 2.f) * Math.sin(45 * Math.PI / 180f));
        float strokeWidth = dip2px(1.5f) / 4;
        mPaint.setStrokeWidth(dip2px(1.5f));
        for (int i = 0; i < 360; i = i + mWheelBigSpace) {
            int angle = (int) (360 - (mAnimatedValue * mWheelBigSpace + i));
            float x3 = (float) ((bigRingCenterX - smallRingCenterX) * Math.cos(angle * Math.PI / 180f));
            float y3 = (float) ((bigRingCenterY - smallRingCenterY) * Math.sin(angle * Math.PI / 180f));
            float x4 = (float) ((bigRingCenterX - smallRingCenterX + mWheelLength) * Math.cos(angle * Math.PI / 180f));
            float y4 = (float) ((bigRingCenterY - smallRingCenterY + mWheelLength) * Math.sin(angle * Math.PI / 180f));
            canvas.drawLine(bigRingCenterX + mPadding - x4 + mWheelLength * 2 + strokeWidth,
                    bigRingCenterY + mPadding - y4 + mWheelLength * 2 + strokeWidth,
                    bigRingCenterX + mPadding - x3 + mWheelLength * 2 + strokeWidth,
                    bigRingCenterY + mPadding - y3 + mWheelLength * 2 + strokeWidth,
                    mPaint);
        }
    }

	/**
	 * 绘制大轮
	 * @param canvas
	 */
    private void drawBigRing(Canvas canvas) {
        float strokeWidth = dip2px(1.5f) / 4;
        mPaintRing.setStrokeWidth(dip2px(1.5f));
        canvas.drawCircle(bigRingCenterX + mPadding + mWheelLength * 2 + strokeWidth,
                bigRingCenterY + mPadding + mWheelLength * 2 + strokeWidth,
                bigRingCenterX - smallRingCenterX - strokeWidth, mPaintRing);
        mPaintRing.setStrokeWidth(dip2px(1.5f));
        canvas.drawCircle(bigRingCenterX + mPadding + mWheelLength * 2 + strokeWidth,
                bigRingCenterY + mPadding + mWheelLength * 2 + strokeWidth,
                (bigRingCenterX - smallRingCenterX) / 2 - strokeWidth, mPaintRing);

    }

    /**
     * 绘制轮轴
     * @param canvas
     */
    private void drawAxle(Canvas canvas) {
        for (int i = 0; i < 3; i++) {
            float x3 = (float) ((smallRingCenterX) * Math.cos(i * (360 / 3) * Math.PI / 180f));
            float y3 = (float) ((smallRingCenterY) * Math.sin(i * (360 / 3) * Math.PI / 180f));
            canvas.drawLine(mPadding + smallRingCenterX,
                    mPadding + smallRingCenterY,
                    mPadding + smallRingCenterX - x3,
                    mPadding + smallRingCenterY - y3, mPaintAxle);
        }

        for (int i = 0; i < 3; i++) {
            float x3 = (float) ((bigRingCenterX - smallRingCenterX) * Math.cos(i * (360 / 3) * Math.PI / 180f));
            float y3 = (float) ((bigRingCenterY - smallRingCenterY) * Math.sin(i * (360 / 3) * Math.PI / 180f));
            canvas.drawLine(bigRingCenterX + mPadding + mWheelLength * 2,
                    bigRingCenterY + mPadding + mWheelLength * 2,
                    bigRingCenterX + mPadding + mWheelLength * 2 - x3,
                    bigRingCenterY + mPadding + mWheelLength * 2 - y3,
                    mPaintAxle);
        }
    }
	
	/**
	 * 初始化画笔
	 */
	private void initPaint() {
        mPaintRing = new Paint();
        mPaintRing.setAntiAlias(true);
        mPaintRing.setStyle(Paint.Style.STROKE);
        mPaintRing.setColor(Color.WHITE);
        mPaintRing.setStrokeWidth(dip2px(1.5f));

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(dip2px(1));

        mPaintAxle = new Paint();
        mPaintAxle.setAntiAlias(true);
        mPaintAxle.setStyle(Paint.Style.FILL);
        mPaintAxle.setColor(Color.WHITE);
        mPaintAxle.setStrokeWidth(dip2px(1.5f));
        mPadding = dip2px(5);
        mWheelLength = dip2px(2f);
    }
	
	/**
	 * 开始转动动画
	 */
	public void startAnim() {
        stopAnim();
        startViewAnim(0f, 1f, 300);
    }

	/**
	 * 停止转动动画
	 */
    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();
            valueAnimator.setRepeatCount(1);
            valueAnimator.cancel();
            valueAnimator.end();
            postInvalidate();
        }
    }
    
    /**
     * 初始化动画
     * @param startF
     * @param endF
     * @param time
     * @return
     */
    private ValueAnimator startViewAnim(float startF, final float endF, long time) {
        valueAnimator = ValueAnimator.ofFloat(startF, endF);
        valueAnimator.setDuration(time);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatedValue = (Float) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }
        });
        if (!valueAnimator.isRunning()) {
            valueAnimator.start();

        }
        return valueAnimator;
    }
	
	/**
	 * 将密度转为像素
	 * @param dpValue
	 * @return
	 */
	public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
