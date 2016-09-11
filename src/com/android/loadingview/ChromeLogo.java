package com.android.loadingview;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * Chrome图标转动
 */
public class ChromeLogo extends View{
	
	private Paint mPaintRed, mPaintYellow, mPaintGreen, mPaintBulue, mPaintWhite, mPaintLine;

	private float mWidth = 0f;
	private float mPadding = 0f;

	private ArgbEvaluator evaluator;// 颜色渐变控制
	private int endColor = Color.rgb(0, 0, 0);
	private int startYellowColor = Color.argb(100, 253, 197, 53);

	private int startGreenColor = Color.argb(100, 27, 147, 76);
	private int startRedColor = Color.argb(100, 211, 57, 53);

	private int lineColor;
	RotateAnimation mProgerssRotateAnim;

	public ChromeLogo(Context context) {
		// super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}
	
	public ChromeLogo(Context context, AttributeSet attrs) {
		// super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}
	
	public ChromeLogo(Context context, AttributeSet attrs, int defStyle) {
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

		mPadding = dip2px(1);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	
		// 关闭view的硬件加速
		setLayerType(LAYER_TYPE_SOFTWARE, null);
		canvas.save();
		
		drawSector(canvas);
		drawTriangle(canvas);
		drawCircle(canvas);
		canvas.restore();
	}
	
	/**
	 * 将圆分成三个扇形
	 * @param canvas
	 */
	private void drawSector(Canvas canvas) {
		RectF rectF = new RectF(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
		canvas.drawArc(rectF, -30, 120, true, mPaintYellow);
		canvas.drawArc(rectF, 90, 120, true, mPaintGreen);
		canvas.drawArc(rectF, 210, 120, true, mPaintRed);
	}

	/**
	 * 画三个等边三角形组成的大三角形正好是内切三角形
	 * @param canvas
	 */
	private void drawTriangle(Canvas canvas) {
		Point point1 = getPoint((mWidth / 2 - mPadding) / 2, 90);
		Point point2 = getPoint((mWidth / 2 - mPadding), 150);
		Point point3 = getPoint((mWidth / 2 - mPadding) / 2, 210);
		Point point4 = getPoint((mWidth / 2 - mPadding), 270);
		Point point5 = getPoint((mWidth / 2 - mPadding) / 2, 330);
		Point point6 = getPoint((mWidth / 2 - mPadding), 30);
		Path pathYellow = new Path();
		pathYellow.moveTo(mWidth / 2 - point1.x, mWidth / 2 - point1.y);
		pathYellow.lineTo(mWidth / 2 - point2.x, mWidth / 2 - point2.y);
		pathYellow.lineTo(mWidth / 2 - point3.x, mWidth / 2 - point3.y);
		pathYellow.close();
		Path pathGreen = new Path();
		pathGreen.moveTo(mWidth / 2 - point3.x, mWidth / 2 - point3.y);
		pathGreen.lineTo(mWidth / 2 - point4.x, mWidth / 2 - point4.y);
		pathGreen.lineTo(mWidth / 2 - point5.x, mWidth / 2 - point5.y);
		pathGreen.close();
		Path pathRed = new Path();
		pathRed.moveTo(mWidth / 2 - point5.x, mWidth / 2 - point5.y);
		pathRed.lineTo(mWidth / 2 - point6.x, mWidth / 2 - point6.y);
		pathRed.lineTo(mWidth / 2 - point1.x, mWidth / 2 - point1.y);
		pathRed.close();
		canvas.drawPath(pathGreen, mPaintGreen);
		canvas.drawPath(pathRed, mPaintRed);
		canvas.drawPath(pathYellow, mPaintYellow);

		// 扇形交接处隐形效果
		for (int i = 0; i < Math.abs(mWidth / 2 - point2.y) / 2f; i++) {

			int fraction = 35 - i;
			if (fraction > 0) {
				lineColor = (Integer) evaluator.evaluate(fraction / 100f, startYellowColor, endColor);
				mPaintLine.setColor(lineColor);
			} else {
				mPaintLine.setColor(Color.argb(0, 0, 0, 0));
			}

			canvas.drawLine(mWidth / 2, point2.y + i, mWidth / 2 - point2.x * 8f / 10f, mWidth / 2 - point2.y,
					mPaintLine);

		}

		for (int i = 0; i < Math.abs(point3.x) / 2f; i++) {
			int fraction = 35 - i;
			if (fraction > 0) {
				lineColor = (Integer) evaluator.evaluate(fraction / 100f, startGreenColor, endColor);
				mPaintLine.setColor(lineColor);
			} else

				mPaintLine.setColor(Color.argb(0, 0, 0, 0));

			canvas.drawLine(mWidth / 2 - point3.x - i, mWidth / 2 - point3.y, mWidth / 2 - point4.x,
					mWidth / 2 - point4.y, mPaintLine);

		}

		for (int i = 0; i < Math.abs(mWidth / 2 - point5.x) / 2f; i++) {
			int fraction = 30 - i;
			if (fraction > 0) {
				lineColor = (Integer) evaluator.evaluate(fraction / 100f, startRedColor, endColor);
				mPaintLine.setColor(lineColor);
			} else

				mPaintLine.setColor(Color.argb(0, 0, 0, 0));
			canvas.drawLine(mWidth / 2 - point5.x + i, mWidth / 2 - point5.y, mWidth / 2 - point6.x,
					mWidth / 2 - point6.y, mPaintLine);

		}

	}

	/**
	 * 画中心的圆覆盖
	 * @param canvas
	 */
	private void drawCircle(Canvas canvas) {
		canvas.drawCircle(mWidth / 2, mWidth / 2, (mWidth / 2 - mPadding) / 2, mPaintWhite);
		canvas.drawCircle(mWidth / 2, mWidth / 2, (mWidth / 2 - mPadding) / 2 / 6 * 5, mPaintBulue);
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
	 * 将密度转为像素
	 * @param dpValue
	 * @return
	 */
	public int dip2px(float dpValue) {
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	/**
	 * 初始化画笔、动画
	 */
	private void initPaint() {
		evaluator = new ArgbEvaluator();

		mPaintRed = new Paint();
		mPaintRed.setAntiAlias(true);
		mPaintRed.setStyle(Paint.Style.FILL);
		mPaintRed.setColor(Color.rgb(211, 57, 53));

		mPaintYellow = new Paint();
		mPaintYellow.setAntiAlias(true);
		mPaintYellow.setStyle(Paint.Style.FILL);
		mPaintYellow.setColor(Color.rgb(253, 197, 53));

		mPaintGreen = new Paint();
		mPaintGreen.setAntiAlias(true);
		mPaintGreen.setStyle(Paint.Style.FILL);
		mPaintGreen.setColor(Color.rgb(27, 147, 76));

		mPaintBulue = new Paint();
		mPaintBulue.setAntiAlias(true);
		mPaintBulue.setStyle(Paint.Style.FILL);
		mPaintBulue.setColor(Color.rgb(61, 117, 242));

		mPaintWhite = new Paint();
		mPaintWhite.setAntiAlias(true);
		mPaintWhite.setStyle(Paint.Style.FILL);
		mPaintWhite.setColor(Color.WHITE);

		mPaintLine = new Paint();
		mPaintLine.setAntiAlias(true);
		mPaintLine.setStyle(Paint.Style.FILL);
		mPaintLine.setColor(Color.argb(30, 0, 0, 0));

		mProgerssRotateAnim = new RotateAnimation(0f, 360f, android.view.animation.Animation.RELATIVE_TO_SELF, 0.5f,
				android.view.animation.Animation.RELATIVE_TO_SELF, 0.5f);
		mProgerssRotateAnim.setRepeatCount(-1);
		mProgerssRotateAnim.setInterpolator(new LinearInterpolator());
		mProgerssRotateAnim.setFillAfter(true);
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
}
