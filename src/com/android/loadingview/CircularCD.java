package com.android.loadingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * 圆形（CD）转动
 */
public class CircularCD extends View{
	
	private Paint mPaint;

    private float mWidth = 0f;
    private float mPadding = 0f;

    RotateAnimation mProgerssRotateAnim;
    RectF rectF = new RectF();
    RectF rectF2 = new RectF();
	
	public CircularCD(Context context) {
		// super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}
	
	public CircularCD(Context context, AttributeSet attrs) {
		// super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}

	public CircularCD(Context context, AttributeSet attrs, int defStyle) {
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
		
        mPadding = 5;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		canvas.save();
        mPaint.setStrokeWidth(2);
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2 - mPadding, mPaint);
        mPaint.setStrokeWidth(3);
        canvas.drawCircle(mWidth / 2, mWidth / 2, mPadding, mPaint);

        mPaint.setStrokeWidth(2);
        rectF = new RectF(mWidth / 2 - mWidth / 3, mWidth / 2 - mWidth / 3,
                mWidth / 2 + mWidth / 3, mWidth / 2 + mWidth / 3);
        canvas.drawArc(rectF, 0, 80, false, mPaint);

        canvas.drawArc(rectF, 180, 80, false, mPaint);

        rectF2 = new RectF(mWidth / 2 - mWidth / 4, mWidth / 2 - mWidth / 4,
                mWidth / 2 + mWidth / 4, mWidth / 2 + mWidth / 4);
        canvas.drawArc(rectF2, 0, 80, false, mPaint);
        canvas.drawArc(rectF2, 180, 80, false, mPaint);
        canvas.restore();
	}
	
	/**
	 * 开始转动动画
	 */
	public void startAnim() {
        stopAnim();
        mProgerssRotateAnim.setDuration(1500);
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
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mProgerssRotateAnim = new RotateAnimation(0f, 360f, android.view.animation.Animation.RELATIVE_TO_SELF,
                0.5f, android.view.animation.Animation.RELATIVE_TO_SELF, 0.5f);
        mProgerssRotateAnim.setRepeatCount(-1);
        mProgerssRotateAnim.setInterpolator(new LinearInterpolator());//不停顿
        mProgerssRotateAnim.setFillAfter(true);//停在最后
    }
}
