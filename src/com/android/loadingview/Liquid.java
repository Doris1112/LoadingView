package com.android.loadingview;

import java.util.ArrayList;
import java.util.List;

import com.android.loadingview.liquid.util.BaseController;
import com.android.loadingview.liquid.util.PourFinishController;
import com.android.loadingview.liquid.util.PourFinishListener;
import com.android.loadingview.liquid.util.PourStartController;
import com.android.loadingview.liquid.util.TickController;
import com.android.loadingview.liquid.util.WaveController;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * 盛水效果
 */
public class Liquid extends View{
	
	private PourFinishListener listener;
    private boolean isFillAfter;
    private boolean isAutoPlay;

    private boolean drawFillAfterFlag;
    private Animator startAnim, waveAnim, finishAnim;

    private BaseController startController;
    private WaveController waveController;
    private List<BaseController> finishControllers;

	public Liquid(Context context) {
		// super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}
	
	public Liquid(Context context, AttributeSet attrs) {
		// super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}
	
	public Liquid(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}
	
	/**
     * 
     */
    private void init() {
        startController = new PourStartController();
        startController.setCheckView(this);

        waveController = new WaveController();
        waveController.setCheckView(this);

        PourFinishController finishController = new PourFinishController();
        finishController.setCheckView(this);

        TickController tickController = new TickController();
        tickController.setCheckView(this);

        List<BaseController> finishControllers = new ArrayList<>();
        finishControllers.add(finishController);
        finishControllers.add(tickController);

        this.finishControllers = finishControllers;
    }
    
    public void setFillAfter(boolean fillAfter) {
        this.isFillAfter = fillAfter;
    }

    public boolean isFillAfter() {
        return isFillAfter;
    }

    private void onFillAfter(Canvas canvas) {
        //draw the last frame of the last controller
        BaseController controller = finishControllers.get(finishControllers.size() - 1);

        if (!controller.isRunning()) {
            controller.draw(canvas);
        }
    }

    public boolean isAutoPlay() {
        return isAutoPlay;
    }

    public void setAutoPlay(boolean autoPlay) {
        isAutoPlay = autoPlay;
    }

    private void onFinishPour() {
        if (listener != null) {
            listener.onPourFinish();
        }
    }

    public void setPourFinishListener(PourFinishListener listener) {
        this.listener = listener;
        if (waveController != null) {
            waveController.setPourFinishListener(listener);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int width = getWidth();
        int height = getHeight();

        startController.getMeasure(width, height);
        waveController.getMeasure(width, height);

        for (BaseController controller : finishControllers) {
            controller.getMeasure(width, height);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        BaseController controller = getRunningController();
        if (controller != null) {
            controller.draw(canvas);
        }
        //if the fill after flag is on, draw the lastFrame
        if (drawFillAfterFlag) {
            onFillAfter(canvas);
        }
    }


    private BaseController getRunningController() {
        if (startController.isRunning()) {
            return startController;
        } else if (waveController.isRunning()) {
            return waveController;
        } else {
            for (BaseController controller : finishControllers) {
                if (controller.isRunning()) {
                    return controller;
                }
            }
        }
        return null;
    }


    private Animator buildStartAnimator() {
        Animator animator = startController.getAnimator();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startWaveAnimator();
                if (isAutoPlay()) {
                    Liquid.this.changeProgress(1f);
                }
            }
        });

        return animator;
    }

    private Animator buildFinishAnimator() {
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animators = new ArrayList<>();
        for (BaseController controller : finishControllers) {
            animators.add(controller.getAnimator());
        }
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                onPourEnd();
                onFinishPour();
                Liquid.this.setClickable(true);
            }
        });
        animatorSet.playSequentially(animators);
        return animatorSet;
    }

    private void startWaveAnimator() {
        if (waveAnim == null) {
            waveAnim = waveController.getAnimator();
        }
        waveAnim.start();
    }

    public void startPour() {
        if(startAnim==null) {
            startAnim = buildStartAnimator();
        }
        //clear the fillAfterFlag
        if (drawFillAfterFlag) {
            drawFillAfterFlag = false;
        }

        startAnim.start();
        this.setClickable(false);
    }

    public void finishPour() {
        if (waveController.getLiquidProgress() >= 1f) {
            if (finishAnim == null) {
                finishAnim = buildFinishAnimator();
            }
            finishAnim.start();
        } else {
            changeProgress(1f);
        }
    }

    /**
     * 四结束
     */
    private void onPourEnd() {
        drawFillAfterFlag = isFillAfter();

        if (drawFillAfterFlag) {
            postInvalidate();
        }
    }

    /**
     * 改变进度
     * @param progress
     */
    public void changeProgress(float progress) {
        if (waveController.isRunning()) {
            waveController.changeProgress(progress);
        }
    }

}
