package com.android.test;

import com.android.loadingview.R;
import com.android.loadingview.SpinKitView;
import com.android.loadingview.spinkitview.util.Sprite;
import com.android.loadingview.spinkitview.util.SpriteFactory;
import com.android.loadingview.spinkitview.util.Style;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class Test6 extends Activity implements Colors{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test6);
		
		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new GridAdapter());
		
	}
	
	private class GridAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Style.values().length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView = View.inflate(Test6.this, R.layout.item_loadingview_grid, null);
			
			SpinKitView spinKitView = (SpinKitView) convertView.findViewById(R.id.spin_kit);
			convertView.setBackgroundColor(colors[position % colors.length]);
            position = position % 15;
            Style style = Style.values()[position];
            Sprite drawable = SpriteFactory.create(style);
            spinKitView.setIndeterminateDrawable(drawable);
			
			return convertView;
		}
		
	}
}

