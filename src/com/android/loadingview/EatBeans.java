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
 * 吃货
 */
public class EatBeans extends View{
	
	private Paint mPaint, mPaintEye;

	private float mWidth = 0f;
	private float mHigh = 0f;
	private float mPadding = 5f;

	private float eatErWidth = 60f;
	private float eatErPositonX = 0f;
	int eatSpeed = 5;
	private float beansWidth = 10f;

	private float mAngle = 34;
	private float eatErStrtAngle = mAngle;
	private float eatErEndAngle = 360 - 2 * eatErStrtAngle;
	
	ValueAnimator valueAnimator = null;

	public EatBeans(Context context) {
		// super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}
	
	public EatBeans(Context context, AttributeSet attrs) {
		// super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}
	
	public EatBeans(Context context, AttributeSet attrs, int defStyleAttr) {
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
		
		float eatRightX = mPadding + eatErWidth + eatErPositonX;
		RectF rectF = new RectF(mPadding + eatErPositonX, mHigh / 2 - eatErWidth / 2, eatRightX,
				mHigh / 2 + eatErWidth / 2);
		canvas.drawArc(rectF, eatErStrtAngle, eatErEndAngle, true, mPaint);
		canvas.drawCircle(mPadding + eatErPositonX + eatErWidth / 2, mHigh / 2 - eatErWidth / 4, beansWidth / 2,
				mPaintEye);

		int beansCount = (int) ((mWidth - mPadding * 2 - eatErWidth) / beansWidth / 2);
		for (int i = 0; i < beansCount; i++) {

			float x = beansCount * i + beansWidth / 2 + mPadding + eatErWidth;
			if (x > eatRightX) {
				canvas.drawCircle(x, mHigh / 2, beansWidth / 2, mPaint);
			}
		}
	}
	
	/**
	 * 开始动画
	 */
	public void startAnim() {
		stopAnim();
		startViewAnim(0f, 1f, 3500);
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
			eatErPositonX = 0;
			postInvalidate();
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

		mPaintEye = new Paint();
		mPaintEye.setAntiAlias(true);
		mPaintEye.setStyle(Paint.Style.FILL);
		mPaintEye.setColor(Color.BLACK);
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
		valueAnimator.setRepeatCount(ValueAnimator.INFINITE);// 无限循环
		valueAnimator.setRepeatMode(ValueAnimator.RESTART);
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				float mAnimatedValue = (Float) valueAnimator.getAnimatedValue();
				eatErPositonX = (mWidth - 2 * mPadding - eatErWidth) * mAnimatedValue;
				eatErStrtAngle = mAngle * (1 - (mAnimatedValue * eatSpeed - (int) (mAnimatedValue * eatSpeed)));
				eatErEndAngle = 360 - eatErStrtAngle * 2;
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
