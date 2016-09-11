package com.android.loadingview;

import java.util.ArrayList;
import java.util.List;

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
 * 画五角星阵图效果
 */
public class FinePoiStar extends View {

	private float mWidth = 0f;
	private float mPadding = 0f;
	private Paint mPaintLine, mPaintCircle;
	private int hornCount = 5;

	private List<Point> listPoint = new ArrayList<Point>();

	/**
	 *  设置是否显示实时画下线条，默认显示
	 */
	private boolean isDrawPath = true;
	RectF rectF = new RectF();
	
	ValueAnimator valueAnimator = null;
	float mAnimatedValue = 0.75f;
	
	public FinePoiStar(Context context) {
		// super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}
	
	public FinePoiStar(Context context, AttributeSet attrs) {
		// super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}
	
	public FinePoiStar(Context context, AttributeSet attrs, int defStyleAttr) {
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

		mPadding = dip2px(1);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		// 关闭view的硬件加速
		setLayerType(LAYER_TYPE_SOFTWARE, null);
		listPoint.clear();
		for (int i = 0; i < hornCount; i++) {
			Point p = getPoint(mWidth / 2 - mPadding, (90 - 360 / hornCount) + 360 / hornCount * i);
			listPoint.add(p);
		}
		Point cp = null;
		float currenttime = (mAnimatedValue * 10 - (int) (mAnimatedValue * 10));
		if (mAnimatedValue >= 0 && mAnimatedValue <= 0.1f) {
			cp = drawOneEdge(currenttime, 1f, listPoint.get(0), listPoint.get(2));
			if (isDrawPath)
				drawPathEdge(canvas, listPoint.get(0), cp);
			else {
				canvas.drawCircle(mWidth / 2 - cp.x, mWidth / 2 - cp.y, mPadding, mPaintLine);
			}
		} else if (mAnimatedValue > 0.1f && mAnimatedValue <= 0.2f) {
			cp = drawOneEdge(currenttime, 1f, listPoint.get(2), listPoint.get(4));
			if (isDrawPath) {
				drawEdge(canvas, 1);
				drawPathEdge(canvas, listPoint.get(2), cp);
			} else
				canvas.drawCircle(mWidth / 2 - cp.x, mWidth / 2 - cp.y, mPadding, mPaintLine);
		} else if (mAnimatedValue > 0.2f && mAnimatedValue <= 0.3f) {
			cp = drawOneEdge(currenttime, 1f, listPoint.get(4), listPoint.get(1));
			if (isDrawPath) {
				drawEdge(canvas, 2);
				drawPathEdge(canvas, listPoint.get(4), cp);
			} else
				canvas.drawCircle(mWidth / 2 - cp.x, mWidth / 2 - cp.y, mPadding, mPaintLine);
		} else if (mAnimatedValue > 0.3f && mAnimatedValue <= 0.4f) {
			cp = drawOneEdge(currenttime, 1f, listPoint.get(1), listPoint.get(3));
			if (isDrawPath) {
				drawEdge(canvas, 3);
				drawPathEdge(canvas, listPoint.get(1), cp);
			} else
				canvas.drawCircle(mWidth / 2 - cp.x, mWidth / 2 - cp.y, mPadding, mPaintLine);
		} else if (mAnimatedValue > 0.4f && mAnimatedValue <= 0.5f) {
			cp = drawOneEdge(currenttime, 1f, listPoint.get(3), listPoint.get(0));
			if (isDrawPath) {
				drawEdge(canvas, 4);
				drawPathEdge(canvas, listPoint.get(3), cp);
			} else
				canvas.drawCircle(mWidth / 2 - cp.x, mWidth / 2 - cp.y, mPadding, mPaintLine);
		} else if (mAnimatedValue > 0.5f && mAnimatedValue <= 0.75f) {
			drawEdge(canvas, 5);
			rectF = new RectF(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
			canvas.drawArc(rectF, -180 + ((90 - 360 / hornCount)), 360 / 0.25f * (mAnimatedValue - 0.5f), false,
					mPaintCircle);
		} else {
			mPaintCircle.setStrokeWidth(dip2px(1.5f));
			mPaintLine.setShadowLayer(1, 1, 1, Color.WHITE);
			drawEdge(canvas, 5);
			rectF = new RectF(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
			canvas.drawArc(rectF, -180 + ((90 - 360 / hornCount)), 360, false, mPaintCircle);
		}
		mPaintCircle.setStrokeWidth(dip2px(1.0f));
		mPaintLine.setShadowLayer(0, 1, 1, Color.WHITE);
	}
	
	/**
	 * 绘制边
	 * @param canvas
	 * @param edgeCount
	 */
	private void drawEdge(Canvas canvas, int edgeCount) {
		switch (edgeCount) {
		case 1:
			drawFirstEdge(canvas);
			break;
		case 2:
			drawFirstEdge(canvas);
			drawSecondEdge(canvas);
			break;
		case 3:
			drawFirstEdge(canvas);
			drawSecondEdge(canvas);
			drawThirdEdge(canvas);
			break;
		case 4:
			drawFirstEdge(canvas);
			drawSecondEdge(canvas);
			drawThirdEdge(canvas);
			drawFourthEdge(canvas);
			break;
		case 5:
			drawFirstEdge(canvas);
			drawSecondEdge(canvas);
			drawThirdEdge(canvas);
			drawFourthEdge(canvas);
			drawFifthEdge(canvas);
			break;
		}
	}
	
	/**
	 * 获取绘制一条边的点
	 * @param canvas
	 */
	private Point drawOneEdge(float currenttime, float alltime, Point startP, Point endP) {
		float x = startP.x - (startP.x - endP.x) / alltime * currenttime;
		float y = startP.y - (startP.y - endP.y) / alltime * currenttime;
		Point resPoint = new Point(x, y);
		return resPoint;
	}

	/**
	 * 绘制第一条边
	 * @param canvas
	 */
	private void drawFirstEdge(Canvas canvas) {
		canvas.drawLine(mWidth / 2 - listPoint.get(0).x, mWidth / 2 - listPoint.get(0).y,
				mWidth / 2 - listPoint.get(2).x, mWidth / 2 - listPoint.get(2).y, mPaintLine);
	}

	/**
	 * 绘制第二条边
	 * @param canvas
	 */
	private void drawSecondEdge(Canvas canvas) {
		canvas.drawLine(mWidth / 2 - listPoint.get(2).x, mWidth / 2 - listPoint.get(2).y,
				mWidth / 2 - listPoint.get(4).x, mWidth / 2 - listPoint.get(4).y, mPaintLine);
	}

	/**
	 * 绘制第三条边
	 * @param canvas
	 */
	private void drawThirdEdge(Canvas canvas) {
		canvas.drawLine(mWidth / 2 - listPoint.get(4).x, mWidth / 2 - listPoint.get(4).y,
				mWidth / 2 - listPoint.get(1).x, mWidth / 2 - listPoint.get(1).y, mPaintLine);
	}

	/**
	 * 绘制第四条边
	 * @param canvas
	 */
	private void drawFourthEdge(Canvas canvas) {
		canvas.drawLine(mWidth / 2 - listPoint.get(1).x, mWidth / 2 - listPoint.get(1).y,
				mWidth / 2 - listPoint.get(3).x, mWidth / 2 - listPoint.get(3).y, mPaintLine);
	}

	/**
	 * 绘制第五条边
	 * @param canvas
	 */
	private void drawFifthEdge(Canvas canvas) {
		canvas.drawLine(mWidth / 2 - listPoint.get(3).x, mWidth / 2 - listPoint.get(3).y,
				mWidth / 2 - listPoint.get(0).x, mWidth / 2 - listPoint.get(0).y, mPaintLine);
	}

	/**
	 * 绘制边路线
	 * @param canvas
	 * @param start
	 * @param end
	 */
	private void drawPathEdge(Canvas canvas, Point start, Point end) {
		canvas.drawLine(mWidth / 2 - start.x, mWidth / 2 - start.y, mWidth / 2 - end.x, mWidth / 2 - end.y, mPaintLine);
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
			mAnimatedValue = 0.75f;
			postInvalidate();
		}
	}

	/**
	 * 设置是否显示实时画下线条，默认显示
	 * @param isDrawPath
	 */
	public void setDrawPath(boolean isDrawPath) {
		this.isDrawPath = isDrawPath;
	}
	
	/**
	 * 初始化画笔
	 */
	private void initPaint() {
		mPaintLine = new Paint();
		mPaintLine.setAntiAlias(true);
		mPaintLine.setStyle(Paint.Style.FILL);
		mPaintLine.setColor(Color.WHITE);
		mPaintLine.setStrokeWidth(dip2px(1f));

		mPaintCircle = new Paint();
		mPaintCircle.setAntiAlias(true);
		mPaintCircle.setStyle(Paint.Style.STROKE);
		mPaintCircle.setColor(Color.WHITE);
		mPaintCircle.setStrokeWidth(dip2px(1));
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
	
	/**
	 * 将密度转为像素
	 * @param dpValue
	 * @return
	 */
	public int dip2px(float dpValue) {
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	/**
	 * 得到点
	 * @param radius
	 * @param angle
	 * @return
	 */
	private Point getPoint(float radius, float angle) {
		float x = (float) ((radius) * Math.cos(angle * Math.PI / 180f));
		float y = (float) ((radius) * Math.sin(angle * Math.PI / 180f));

		Point p = new Point(x, y);
		return p;
	}
	
	/**
	 * 点
	 */
	private class Point {
		private float x;
		private float y;

		private Point(float x, float y) {
			this.x = x;
			this.y = y;
		}
	}

}