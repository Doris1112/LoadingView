package com.android.test;

import com.android.loadingview.R;
import com.android.loadingview.spinkitview.util.ChasingDots;
import com.android.loadingview.spinkitview.util.Circle;
import com.android.loadingview.spinkitview.util.DoubleBounce;
import com.android.loadingview.spinkitview.util.Wave;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Test7 extends Activity implements Colors{
	
	private Wave mWaveDrawable;
    private Circle mCircleDrawable;
    private ChasingDots mChasingDotsDrawable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test7);
		
		//ProgressBar
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        doubleBounce.setBounds(0, 0, 100, 100);
        doubleBounce.setColor(colors[7]);
        progressBar.setIndeterminateDrawable(doubleBounce);

        //Button
        Button button = (Button) findViewById(R.id.button);
        mWaveDrawable = new Wave();
        mWaveDrawable.setBounds(0, 0, 100, 100);
        //noinspection deprecation
        mWaveDrawable.setColor(Color.parseColor("#FF4081"));
        button.setCompoundDrawables(mWaveDrawable, null, null, null);

        //TextView
        TextView textView = (TextView) findViewById(R.id.text);
        mCircleDrawable = new Circle();
        mCircleDrawable.setBounds(0, 0, 100, 100);
        mCircleDrawable.setColor(Color.WHITE);
        textView.setCompoundDrawables(null, null, mCircleDrawable, null);
        textView.setBackgroundColor(colors[2]);

        //ImageView
        ImageView imageView = (ImageView) findViewById(R.id.image);
        mChasingDotsDrawable = new ChasingDots();
        mChasingDotsDrawable.setColor(Color.WHITE);
        imageView.setImageDrawable(mChasingDotsDrawable);
        imageView.setBackgroundColor(colors[0]);
	}
	
	@Override
    public void onResume() {
        super.onResume();
        mWaveDrawable.start();
        mCircleDrawable.start();
        mChasingDotsDrawable.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        mWaveDrawable.stop();
        mCircleDrawable.stop();
        mChasingDotsDrawable.stop();
    }
}

