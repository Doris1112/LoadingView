package com.android.test;

import com.android.loadingview.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TestActivity extends Activity {

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		context = this;

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, 
				android.R.layout.simple_list_item_1,
				new String[] { "LoadingView", "Liquid", "Bezier", "Drawable",
						"ViewPager", "GridView", "SystemView", "Button" });

		ListView lv_list = (ListView) findViewById(R.id.lv_list);
		lv_list.setAdapter(adapter);
		lv_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = null;
				switch (position) {
				case 0:
					intent = new Intent(context, Test1.class);
					startActivity(intent);
					break;
				case 1:
					intent = new Intent(context, Test2.class);
					startActivity(intent);
					break;
				case 2:
					intent = new Intent(context, Test3.class);
					startActivity(intent);
					break;
				case 3:
					intent = new Intent(context, Test4.class);
					startActivity(intent);
					break;
				case 4:
					intent = new Intent(context, Test5.class);
					startActivity(intent);
					break;
				case 5:
					intent = new Intent(context, Test6.class);
					startActivity(intent);
					break;
				case 6:
					intent = new Intent(context, Test7.class);
					startActivity(intent);
					break;
				case 7:
					intent = new Intent(context, Test8.class);
					startActivity(intent);
					break;
				}
			}
		});
	}
}
