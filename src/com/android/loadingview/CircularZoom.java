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

public class CircularZoom extends View{
	
	private Paint mPaint;

    private float mWidth = 0f;
    private float mHigh = 0f;
    private float mMaxRadius = 8;
    private int circularCount = 3;
    private float mAnimatedValue = 1.0f;
    private int mJumpValue = 0;
    
    ValueAnimator valueAnimator = null;

	public CircularZoom(Context context) {
		// super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}
	
	public CircularZoom(Context context, AttributeSet attrs) {
		// super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}
	
	public CircularZoom(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		initPaint();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		// 设置视图的大小即View本身的大小
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		// 获取宽和高
		mWidth = getMeasuredWidth();
        mHigh = getMeasuredHeight();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		float circularX = mWidth / circularCount;
        for (int i = 0; i < circularCount; i++) {
            if (i == mJumpValue % circularCount) {
                canvas.drawCircle(i * circularX + circularX / 2f, mHigh / 2,
                        mMaxRadius * mAnimatedValue, mPaint);
            } else {
                canvas.drawCircle(i * circularX + circularX / 2f, mHigh / 2,
                        mMaxRadius, mPaint);
            }
        }
	}
	
	/**
	 * 开始动画
	 */
	public void startAnim() {
        stopAnim();
        startViewAnim(0f, 1f, 500);
    }
	
	/**
	 * 停止动画
	 */
    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();
            mAnimatedValue = 0f;
            mJumpValue = 0;
            valueAnimator.setRepeatCount(1);
            valueAnimator.cancel();
            valueAnimator.end();
        }
    }
	
	/**
	 * 初始化画笔
	 */
	private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
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

                if (mAnimatedValue < 0.2) {
                    mAnimatedValue = 0.2f;
                }
                invalidate();
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
                mJumpValue++;
            }
        });
        if (!valueAnimator.isRunning()) {
            valueAnimator.start();

        }
        return valueAnimator;
    }

}
