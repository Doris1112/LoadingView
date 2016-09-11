package com.android.loadingview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 玩球
 */
public class PlayBall extends View {
	
	private Paint mPaint, mPaintCircle, mPaintBall;
    private float mPaintStrokeWidth;
    private float mHigh = 0f;
    private float mWidth = 0f;
    private float quadToStart = 0f;
    private float mRadius = 0f;
    private float mRadiusBall = 0f;
    private float ballY = 0f;
    Path path = new Path();
    
    ValueAnimator valueAnimator = null;

	public PlayBall(Context context) {
		// super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}
	
	public PlayBall(Context context, AttributeSet attrs) {
		// super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}
	
	public PlayBall(Context context, AttributeSet attrs, int defStyleAttr) {
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
		mHigh = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        quadToStart = mHigh / 2;
        mRadius = dip2px(3);
        mPaintStrokeWidth = 2;
        ballY = mHigh / 2;
        mRadiusBall = dip2px(4);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		path=new Path();
        path.moveTo(0 + mRadius * 2 + mPaintStrokeWidth, getMeasuredHeight() / 2);
        path.quadTo(mWidth / 2, quadToStart, mWidth - mRadius * 2 - mPaintStrokeWidth, mHigh / 2);
        mPaint.setStrokeWidth(2);
        canvas.drawPath(path, mPaint);

        mPaintCircle.setStrokeWidth(mPaintStrokeWidth);
        canvas.drawCircle(mRadius + mPaintStrokeWidth, mHigh / 2, mRadius, mPaintCircle);
        canvas.drawCircle(mWidth - mRadius - mPaintStrokeWidth, mHigh / 2, mRadius, mPaintCircle);

        if (ballY - mRadiusBall > mRadiusBall) {
            canvas.drawCircle(mWidth / 2, ballY - mRadiusBall, mRadiusBall, mPaintBall);
        } else {
            canvas.drawCircle(mWidth / 2, mRadiusBall, mRadiusBall, mPaintBall);
        }
	}
	
	private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);

        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setStyle(Paint.Style.STROKE);
        mPaintCircle.setColor(Color.WHITE);

        mPaintBall = new Paint();
        mPaintBall.setAntiAlias(true);
        mPaintBall.setStyle(Paint.Style.FILL);
        mPaintBall.setColor(Color.WHITE);
    }
	
	/**
	 * 开始动画
	 */
	public void startAnim() {
        stopAnim();
        startViewAnim(0f, 1f, 800);
    }

	/**
	 * 停止动画
	 */
    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();
            quadToStart = mHigh / 2;
            ballY = mHigh / 2;
            valueAnimator.setRepeatCount(1);
            valueAnimator.cancel();
            valueAnimator.end();
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
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);//

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (Float) valueAnimator.getAnimatedValue();
                if (value > 0.75) {
                    quadToStart = mHigh / 2 - (1f - (Float) valueAnimator.getAnimatedValue()) * mHigh / 3f;
                } else {
                    quadToStart = mHigh / 2 + (1f - (Float) valueAnimator.getAnimatedValue()) * mHigh / 3f;
                }
                if (value > 0.35f) {
                    ballY = mHigh / 2 - (mHigh / 2 * value);
                } else {
                    ballY = mHigh / 2 + (mHigh / 6 * value);
                }
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
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
