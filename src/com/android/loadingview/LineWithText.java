package com.android.loadingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * 线条百分比进度显示
 */
public class LineWithText extends View {
	
	private Paint mPaint;

    private float mWidth = 0f;
    private float mHigh = 0f;
    private int mVlaue = 0;

    private float mPadding = 5f;

	public LineWithText(Context context) {
		// super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}
	
	public LineWithText(Context context, AttributeSet attrs) {
		// super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}
	
	public LineWithText(Context context, AttributeSet attrs, int defStyleAttr) {
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
		
		String text = mVlaue + "%";
        float textlength = getFontlength(mPaint, text);
        float texthigh = getFontHeight(mPaint, text);

        if (mVlaue == 0) {
            canvas.drawText(text, mPadding, mHigh / 2 + texthigh / 2, mPaint);
            canvas.drawLine(mPadding + textlength, mHigh / 2, mWidth - mPadding, mHigh / 2, mPaint);
        } else if (mVlaue >= 100) {
            canvas.drawText(text, mWidth - mPadding - textlength, mHigh / 2 + texthigh / 2, mPaint);
            canvas.drawLine(mPadding, mHigh / 2, mWidth - mPadding - textlength, mHigh / 2, mPaint);
        } else {
            float w = mWidth - 2 * mPadding - textlength;
            canvas.drawLine(mPadding, mHigh / 2, mPadding + w * mVlaue / 100, mHigh / 2, mPaint);
            canvas.drawLine(mPadding + w * mVlaue / 100 + textlength, mHigh / 2, mWidth - mPadding, mHigh / 2, mPaint);
            canvas.drawText(text, mPadding + w * mVlaue / 100, mHigh / 2 + texthigh / 2, mPaint);
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
        mPaint.setTextSize(dip2px(10));
        mPaint.setStrokeWidth(dip2px(1));
    }
	
	/**
	 * 获取文字长度
	 * @param paint
	 * @param str
	 * @return
	 */
	public float getFontlength(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.width();
    }

	/**
	 * 获取文字高度
	 * @param paint
	 * @param str
	 * @return
	 */
    public float getFontHeight(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.height();

    }
	
    /**
     * 设置进度
     * @param value
     */
	public void setValue(int value) {
        this.mVlaue = value;
        invalidate();
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
