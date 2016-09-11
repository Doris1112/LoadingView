package com.android.test;

import com.android.loadingview.Liquid;
import com.android.loadingview.R;
import com.android.loadingview.liquid.util.PourFinishListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Test2 extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test2);
		
		final Liquid liquid = (Liquid) findViewById(R.id.liquid);
		liquid.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 开始动画
				liquid.startPour();
			}
		});
		liquid.setPourFinishListener(new PourFinishListener() {
			
			@Override
			public void onProgressUpdate(float progress) {
				// TODO Auto-generated method stub
				// 在此改变进度 1-100
			}
			
			@Override
			public void onPourFinish() {
				// TODO Auto-generated method stub
				// 在此执行动画结束事件
			}
		});
		// 加载动画
		liquid.finishPour();
		// 开始动画
		liquid.startPour();
		// 设置是否自动播放 true=自动播放 false=不自动播放
		liquid.setAutoPlay(true);
		// 设置加载完成后是否显示 true=显示 false=不显示
		liquid.setFillAfter(true);
	}
}

