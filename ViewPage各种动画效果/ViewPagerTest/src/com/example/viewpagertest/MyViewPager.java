package com.example.viewpagertest;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyViewPager extends ViewPager {
	private View viewLeft;
	private View viewRight;
	
	private float mAlpha;
	private float mTrans;
	private float mScale;
	private float mRotate;
	
	private static final float MAX_ROTATE = 20.0f;
	private static final float MIN_SCALE = 0.5f;
	private static final float MIN_AlPHA = 0.6f;
	private Map<Integer, View> mChildren = new HashMap<Integer, View>(); 
	
	public MyViewPager(Context context) {
		super(context);
	}

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 逻辑：position实践证明一直是动画中左视图
	 */
	@Override
	protected void onPageScrolled(int position, float offSet, int offsetPixels) {
		Log.i("TAG", "position = " + position + " , offset = " + offSet
				+ " , offsetPixels = " + offsetPixels);
		viewLeft = mChildren.get(position);
		viewRight = mChildren.get(position + 1);
		animStack (viewLeft, viewRight, offSet, offsetPixels);
		super.onPageScrolled(position, offSet, offsetPixels);
	}
	
	/**
	 * 属性动画:自定义平移，透明度，缩放，倾斜
	 * 支持sdk3.0以上
	 * 逻辑：向后滑动，offSet：0~1；向前，offSet：1~0
	 */
	@SuppressLint("NewApi")
	private void animStack(View viewLeft, View viewRight, float offSet,
			int offsetPixels) {
		if (viewRight != null) {
			mAlpha = (1 - MIN_AlPHA) * offSet + MIN_AlPHA;
			mScale = (1 - MIN_SCALE) * offSet + MIN_SCALE;
			mRotate = MAX_ROTATE * (1 - offSet);
			mTrans = -getWidth() - getPageMargin() + offsetPixels;
			viewRight.setAlpha(mAlpha);
			viewRight.setScaleX(mScale);
			viewRight.setScaleY(mScale);
			viewRight.setTranslationX(mTrans);
			
			viewRight.setPivotX(getWidth()/2);
			viewRight.setPivotY(viewRight.getMeasuredHeight());
			viewRight.setRotation(mRotate);
		}
		if (viewLeft != null) {
			//左边的视图 保证一直在上面
			viewLeft.bringToFront();
		}
	}

	public void setViewForPosition(View view, Integer position){
		mChildren.put(position, view);
	}
	
	public void removeViewFromPosition (Integer position) {
		mChildren.remove(position);
	}

}
