package com.android.loadingview.liquid.util;

import java.lang.ref.WeakReference;

import com.android.loadingview.Liquid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Canvas;

/**
 * 盛水基本控制
 */
public abstract class BaseController {
	
	private WeakReference<Liquid> checkView;
	private Animator animator;
	
	int centerX;
	int centerY;
	int radius;
	
	public abstract void draw(Canvas canvas);

	abstract Animator buildAnimator();

	public abstract void reset();

	protected void setRender(float interpolatedTime) {
		getCheckView().invalidate();
	}

	BaseController() {
		animator = buildAnimator();
	}

	public void setCheckView(Liquid checkView) {
		this.checkView = new WeakReference<>(checkView);
	}

	Liquid getCheckView() {
		return checkView != null ? checkView.get() : null;
	}

	public Animator getAnimator() {
		return animator;
	}

	public boolean isRunning() {
		return getAnimator().isRunning();
	}

	public void getMeasure(int width, int height) {
		centerX = width / 2;
		centerY = height / 2;
		radius = width / 4;
	}

	/**
	 * 初始化动画
	 * @param duration
	 * @param interpolator
	 * @return
	 */
	Animator getBaseAnimator(long duration, TimeInterpolator interpolator) {
		ObjectAnimator animator = ObjectAnimator.ofFloat(this, "render", 0.0f, 1.0f);
		animator.setDuration(duration);
		animator.setInterpolator(interpolator);

		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				super.onAnimationStart(animation);
				reset();
			}
		});
		return animator;
	}

}
