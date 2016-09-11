package com.android.test;

import java.util.Timer;
import java.util.TimerTask;

import com.android.loadingview.Battery;
import com.android.loadingview.Block;
import com.android.loadingview.ChromeLogo;
import com.android.loadingview.Circular;
import com.android.loadingview.CircularCD;
import com.android.loadingview.CircularJump;
import com.android.loadingview.CircularRing;
import com.android.loadingview.CircularSmile;
import com.android.loadingview.CircularZoom;
import com.android.loadingview.EatBeans;
import com.android.loadingview.FinePoiStar;
import com.android.loadingview.FunnyBar;
import com.android.loadingview.Gears;
import com.android.loadingview.GearsTwo;
import com.android.loadingview.Ghost;
import com.android.loadingview.LineWithText;
import com.android.loadingview.News;
import com.android.loadingview.PlayBall;
import com.android.loadingview.R;
import com.android.loadingview.Wifi;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class Test1 extends Activity {

	private LineWithText mLineWithText;
	private Battery mBattery;
	private News mNews;
	
	private boolean mPlayBallIsRunning = false, mCircularRingIsRunning = false, mCircularIsRunning = false,
			mCircularJumpIsRunning = false, mCircularZoomIsRunning = false, mLineWithTextIsRunning = false,
			mEatBeansIsRunning = false, mCircularCDIsRunning = false, mCircularSmileIsRunning = false,
			mGearsIsRunning = false, mGearsTwoIsRunning = false, mFinePoiStarIsRunning = false,
			mChromeLogoIsRunning = false, mBatteryIsRunning = false, mWifiIsRunning = false, mNewsIsRunning = false,
			mBlockIsRunning = false, mGhostIsRunning = false, mFunnyBarIsRunning = false;

	private Timer mTimerLineWithText = new Timer();// 定时器
	private Timer mTimerNews = new Timer();// 定时器
	
	private int mValueLineWithText = 0;
	private int mValueNews = 100;
	
	private Handler mHandle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 2)
				mLineWithText.setValue(msg.arg1);
			else if (msg.what == 1) {
				mNews.setValue(msg.arg1);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test1);

		mLineWithText = (LineWithText) findViewById(R.id.lv_linetext);
		mNews = (News) findViewById(R.id.lv_news);
		mBattery = (Battery) findViewById(R.id.lv_battery);
		// 设置电池为垂直摆放
		mBattery.setBatteryOrientation(Battery.BatteryOrientation.VERTICAL);
		// 设置电池为横向摆放
		// mBattery.setBatteryOrientation(Battery.BatteryOrientation.HORIZONTAL);
		// 设置电量
		mBattery.setValue(50);
		// 设置是否显示电量百分比 false=不显示 true=显示
		mBattery.setShowNum(false);
	}

	/**
	 * 页面LoadingView点击开始动画
	 * 
	 * @param view
	 */
	public void startAnim(View v) {
		if (v instanceof Circular) {
			if(mCircularIsRunning){
				((Circular) v).stopAnim();
				mCircularIsRunning = false;
			}else{
				((Circular) v).startAnim();
				mCircularIsRunning = true;
			}
		} else if (v instanceof CircularCD) {
			if(mCircularCDIsRunning){
				((CircularCD) v).stopAnim();
				mCircularCDIsRunning = false;
			}else{
				((CircularCD) v).startAnim();
				mCircularCDIsRunning = true;
			}
		} else if (v instanceof CircularSmile) {
			if(mCircularSmileIsRunning){
				((CircularSmile) v).stopAnim();
				mCircularSmileIsRunning = false;
			}else{
				((CircularSmile) v).startAnim();
				mCircularSmileIsRunning = true;
			}
		} else if (v instanceof CircularRing) {
			if(mCircularRingIsRunning){
				((CircularRing) v).stopAnim();
				mCircularRingIsRunning = false;
			}else{
				((CircularRing) v).startAnim();
				mCircularRingIsRunning = true;
			}
		} else if (v instanceof CircularZoom) {
			if(mCircularZoomIsRunning){
				((CircularZoom) v).stopAnim();
				mCircularZoomIsRunning = false;
			}else{
				((CircularZoom) v).startAnim();
				mCircularZoomIsRunning = true;
			}
		} else if (v instanceof CircularJump) {
			if(mCircularJumpIsRunning){
				mCircularJumpIsRunning = false;
				((CircularJump) v).stopAnim();
			}else{
				mCircularJumpIsRunning = true;
				((CircularJump) v).startAnim();
			}
		} else if (v instanceof EatBeans) {
			if(mEatBeansIsRunning){
				mEatBeansIsRunning = false;
				((EatBeans) v).stopAnim();
			}else{
				mEatBeansIsRunning = true;
				((EatBeans) v).startAnim();
			}
		} else if (v instanceof PlayBall) {
			if(mPlayBallIsRunning){
				mPlayBallIsRunning = false;
				((PlayBall) v).stopAnim();
			}else{
				mPlayBallIsRunning = true;
				((PlayBall) v).startAnim();
			}
		} else if (v instanceof LineWithText) {
			if(mLineWithTextIsRunning){
				mLineWithTextIsRunning = false;
				stopLineWithTextAnim();
			}else{
				mLineWithTextIsRunning = true;
				startLineWithTextAnim();
			}
		} else if (v instanceof Gears) {
			if(mGearsIsRunning){
				mGearsIsRunning = false;
				((Gears) v).stopAnim();
			}else{
				mGearsIsRunning = true;
				((Gears) v).startAnim();
			}
		} else if (v instanceof GearsTwo) {
			if(mGearsTwoIsRunning){
				mGearsTwoIsRunning = false;
				((GearsTwo) v).stopAnim();
			}else{
				mGearsTwoIsRunning = true;
				((GearsTwo) v).startAnim();
			}
		} else if (v instanceof FinePoiStar) {
			if(mFinePoiStarIsRunning){
				mFinePoiStarIsRunning = false;
				((FinePoiStar) v).stopAnim();
			}else{
				mFinePoiStarIsRunning = true;
				// 设置是否显示绘制路线 true=显示 false=不显示 
				((FinePoiStar) v).setDrawPath(true);
				((FinePoiStar) v).startAnim();
			}
			
		} else if (v instanceof ChromeLogo) {
			if(mChromeLogoIsRunning){
				mChromeLogoIsRunning = false;
				((ChromeLogo) v).stopAnim();
			}else{
				mChromeLogoIsRunning = true;
				((ChromeLogo) v).startAnim();
			}
		} else if (v instanceof Battery) {
			if(mBatteryIsRunning){
				mBatteryIsRunning = false;
				((Battery) v).stopAnim();
			}else{
				mBatteryIsRunning = true;
				((Battery) v).startAnim();
			}
		} else if (v instanceof Wifi) {
			if(mWifiIsRunning){
				mWifiIsRunning = false;
				((Wifi) v).stopAnim();
			}else{
				mWifiIsRunning = true;
				((Wifi) v).startAnim();
			}
		} else if (v instanceof News) {
			if(mNewsIsRunning){
				stopNewsAnim();
				mNewsIsRunning = false;
			}else{
				startNewsAnim();
				mNewsIsRunning = true;
			}
		} else if (v instanceof Block) {
			if(mBlockIsRunning){
				mBlockIsRunning = false;
				((Block) v).stopAnim();
			}else{
				mBlockIsRunning = true;
				((Block) v).startAnim();
			}
		}else if(v instanceof Ghost){
			if(mGhostIsRunning){
				mGhostIsRunning = false;
				((Ghost) v).stopAnim();
			}else{
				mGhostIsRunning = true;
				((Ghost) v).startAnim();
			}
		}else  if(v instanceof FunnyBar){
			if(mFunnyBarIsRunning){
				mFunnyBarIsRunning = false;
				((FunnyBar) v).stopAnim();
			}else{
				mFunnyBarIsRunning = true;
				((FunnyBar) v).startAnim();
			}
		}
	}
	
	private void startLineWithTextAnim() {
		mValueLineWithText = 0;
		if (mTimerLineWithText != null) {
			mTimerLineWithText.cancel();// 退出之前的mTimer
		}
		mTimerLineWithText = new Timer();
		timerTaskLineWithText();
	}
	
	public void timerTaskLineWithText() {
		mTimerLineWithText.schedule(new TimerTask() {
			@Override
			public void run() {
				if (mValueLineWithText < 100) {

					mValueLineWithText++;
					Message msg = mHandle.obtainMessage(2);
					msg.arg1 = mValueLineWithText;
					mHandle.sendMessage(msg);

				} else {
					mTimerLineWithText.cancel();
				}
			}
		}, 0, 50);
	}
	
	private void startNewsAnim() {
		mValueNews = 0;
		if (mTimerNews != null) {
			mTimerNews.cancel();
		}
		mTimerNews = new Timer();
		timerTaskNews();
	}
	
	public void timerTaskNews() {
		mTimerNews.schedule(new TimerTask() {
			@Override
			public void run() {
				if (mValueNews < 100) {
					mValueNews++;
					Message msg = mHandle.obtainMessage(1);
					msg.arg1 = mValueNews;
					mHandle.sendMessage(msg);
				} else {
					mTimerNews.cancel();
				}
			}
		}, 0, 10);
	}
	
	private void stopLineWithTextAnim() {
		if (mTimerLineWithText != null) {
			mTimerLineWithText.cancel();// 退出之前的mTimer
			mNews.setValue(mValueNews);
		}
	}
	
	private void stopNewsAnim() {
		mNews.stopAnim();
		if (mTimerNews != null) {
			mTimerNews.cancel();
			mLineWithText.setValue(mValueLineWithText);
		}
	}
}
