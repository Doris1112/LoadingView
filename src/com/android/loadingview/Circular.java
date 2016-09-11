package com.android.loadingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * 圆形（太阳）转动
 */
public class Circular extends View{
	
	private Paint mPaint;

    private float mWidth = 0f;
    private int mStartAngle = 0;
    private float mMaxRadius = 4;
    RotateAnimation mProgerssRotateAnim;
	
	public Circular(Context context) {
		// super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}
	
	public Circular(Context context, AttributeSet attrs) {
		// super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}

	public Circular(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
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

		// 获取最大半径
        mMaxRadius = mWidth / 30f;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		for (int i = 0; i < 9; i++) {
            float x2 = (float) ((mWidth / 2.f - mMaxRadius) * Math.cos(mStartAngle + 45f * i * Math.PI / 180f));
            float y2 = (float) ((mWidth / 2.f - mMaxRadius) * Math.sin(mStartAngle + 45f * i * Math.PI / 180f));
            canvas.drawCircle(mWidth / 2.f - x2, mWidth / 2.f - y2, mMaxRadius, mPaint);
        }

        canvas.drawCircle(mWidth / 2.f, mWidth / 2.f, mWidth / 2.f - mMaxRadius * 6, mPaint);
	}
	
	/**
	 * 开始转动动画
	 */
	public void startAnim() {
        stopAnim();
        mProgerssRotateAnim.setDuration(3500);
        startAnimation(mProgerssRotateAnim);
    }

	/**
	 * 停止转动动画
	 */
    public void stopAnim() {
        clearAnimation();
    }
	
	/**
	 * 初始化画笔、动画
	 */
	private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);

        mProgerssRotateAnim = new RotateAnimation(0f, 360f, android.view.animation.Animation.RELATIVE_TO_SELF,
                0.5f, android.view.animation.Animation.RELATIVE_TO_SELF, 0.5f);
        mProgerssRotateAnim.setRepeatCount(-1);
        mProgerssRotateAnim.setInterpolator(new LinearInterpolator());//不停顿
        mProgerssRotateAnim.setFillAfter(true);//停在最后
    }

}
