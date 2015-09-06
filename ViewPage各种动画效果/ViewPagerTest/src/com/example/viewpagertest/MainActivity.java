package com.example.viewpagertest;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class MainActivity extends Activity {
 
	private MyViewPager mViewPager;
	
	private int[] mImgIds = {R.drawable.guide_image1, R.drawable.guide_image2, R.drawable.guide_image3};
	private List<ImageView> mImgViews = new ArrayList<ImageView>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mViewPager = (MyViewPager) this.findViewById(R.id.viewPager);
		//为ViewPager添加适配器，切换效果
		mViewPager.setAdapter(new PagerAdapter() {
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				ImageView iv = new ImageView(MainActivity.this);
				iv.setImageResource(mImgIds[position]);
				iv.setScaleType(ScaleType.CENTER_CROP);
				container.addView(iv);
				mImgViews.add(iv);
				mViewPager.setViewForPosition(iv, position);
				return iv;
			}
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(mImgViews.get(position));
				mViewPager.removeViewFromPosition(position);
			}
			@Override
			public boolean isViewFromObject(View view, Object obj) {
				return view == obj;
			}
			@Override
			public int getCount() {
				return mImgIds.length;
			}
		});
	}

}
