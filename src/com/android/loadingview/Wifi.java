package com.android.loadingview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * wifi搜索效果
 */
public class Wifi extends View {

	private float mWidth = 0f;
	private Paint mPaint;
	private int signalSize = 4;
	
	private ValueAnimator valueAnimator;
    private float mAnimatedValue = 0.9f;

	public Wifi(Context context) {
		// super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}

	public Wifi(Context context, AttributeSet attrs) {
		// super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}

	public Wifi(Context context, AttributeSet attrs, int defStyleAttr) {
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
		canvas.translate(0, mWidth / signalSize);
		mPaint.setStrokeWidth(mWidth / signalSize / 2 / 2 / 2);
		int scale = (int) ((mAnimatedValue * signalSize - (int) (mAnimatedValue * signalSize)) * signalSize) + 1;
		RectF rect = null;
		float signalRadius = mWidth / 2 / signalSize;
		for (int i = 0; i < signalSize; i++) {
			if (i >= signalSize - scale) {
				float radius = signalRadius * i;
				rect = new RectF(radius, radius, mWidth - radius, mWidth - radius);
				if (i < signalSize - 1) {
					mPaint.setStyle(Paint.Style.STROKE);
					canvas.drawArc(rect, -135, 90, false, mPaint);
				} else {
					mPaint.setStyle(Paint.Style.FILL);
					canvas.drawArc(rect, -135, 90, true, mPaint);
				}
			}
		}
		canvas.restore();
	}

	/**
	 * 初始化画笔
	 */
	private void initPaint() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setColor(Color.WHITE);
	}
	
	/**
	 * 开始动画
	 */
	public void startAnim() {
        stopAnim();
        startViewAnim(0f, 1f, 6000);
    }
	
	/**
	 * 停止动画
	 */
	public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();

            valueAnimator.setRepeatCount(1);
            valueAnimator.cancel();
            valueAnimator.end();
            mAnimatedValue = 0.9f;
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
            }
        });
        if (!valueAnimator.isRunning()) {
            valueAnimator.start();
        }
        return valueAnimator;
    }

}
