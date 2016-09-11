package com.android.test;

import com.android.loadingview.Metaball;
import com.android.loadingview.R;

import android.app.Activity;
import android.os.Bundle;

public class Test3 extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test3);
		
		final Metaball metaball = (Metaball) findViewById(R.id.metaball);
		// 设置绘制模式 1=填充 0=边框
		metaball.setPaintMode(1);
		
	}
}

