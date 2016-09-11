package com.android.test;

import com.android.loadingview.R;
import com.android.loadingview.SpinKitView;
import com.android.loadingview.spinkitview.util.ArgbEvaluator;
import com.android.loadingview.spinkitview.util.Sprite;
import com.android.loadingview.spinkitview.util.SpriteFactory;
import com.android.loadingview.spinkitview.util.Style;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Test5 extends Activity implements Colors {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test5);

		ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
		viewPager.setOffscreenPageLimit(0);
		viewPager.setAdapter(new PagerAdapter() {
			@Override
			public int getCount() {
				return Style.values().length;
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_loadingview_pager, null);

				SpinKitView spinKitView = (SpinKitView) view.findViewById(R.id.spin_kit);
				TextView name = (TextView) view.findViewById(R.id.name);
				Style style = Style.values()[position];
				name.setText(style.name());
				Sprite drawable = SpriteFactory.create(style);
				spinKitView.setIndeterminateDrawable(drawable);
				container.addView(view);

				return view;
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView((View) object);
			}
		});

		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				int color = (int) ArgbEvaluator.getInstance().evaluate(positionOffset, colors[position % colors.length],
						colors[(position + 1) % colors.length]);
				getWindow().getDecorView().setBackgroundColor(color);
			}

			@Override
			public void onPageSelected(int position) {
				getWindow().getDecorView().setBackgroundColor(colors[position % colors.length]);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}
}
