/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ps.sleek;

import android.app.WallpaperManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.ps.sleek.adapter.ParallaxAdapter;
import com.ps.sleek.broadcastreceivers.ApplicationReceiver;
import com.ps.sleek.manager.ApplicationManager;
import com.ps.sleek.utils.DimensionUtils;
import com.ps.sleek.view.ViewPagerParallax;

public class SleekLauncher extends FragmentActivity implements OnClickListener {

	/**
	 * Keys during freeze/thaw.
	 */
	private static final String KEY_SAVE_GRID_OPENED = "grid.opened";
	
	private final ApplicationReceiver mApplicationsReceiver = new ApplicationReceiver();
	
	private View mShowApplications;

	private ViewPagerParallax viewPager;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
		
		setContentView(R.layout.activity_launcher);
		
		viewPager = (ViewPagerParallax) findViewById(R.id.pager);
		viewPager.setAdapter(new ParallaxAdapter(getSupportFragmentManager()));
		
		bindButtons();
		
		Drawable drawable = WallpaperManager.getInstance(this).getDrawable();
		Log.d("wallpaper", drawable.getIntrinsicWidth() + "x" + drawable.getIntrinsicHeight() + "," + DimensionUtils.getWidthPixels(this) + "x" + DimensionUtils.getHeightPixels(this));
		
		registerIntentReceivers();
	}
	
	private void bindButtons() {
		mShowApplications = findViewById(R.id.app_button);
		mShowApplications.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if (viewPager.getVisibility() != View.VISIBLE) {
			viewPager.setVisibility(View.VISIBLE);
		} else {
			viewPager.setVisibility(View.INVISIBLE);
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();

		// Remove the callback for the cached drawables or we leak
		// the previous Home screen on orientation change
		ApplicationManager.getInstance(this).teardown();

		unregisterReceiver(mApplicationsReceiver);
	}

	private void registerIntentReceivers() {
		mApplicationsReceiver.register(this);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if(savedInstanceState == null) {
			return;
		}
		final boolean opened = savedInstanceState.getBoolean(KEY_SAVE_GRID_OPENED, false);
		if (opened) {
			viewPager.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(KEY_SAVE_GRID_OPENED,
				viewPager.getVisibility() == View.VISIBLE);
	}

	@Override
	public void onBackPressed() {
		if (viewPager.getVisibility() == View.VISIBLE) {
			viewPager.setVisibility(View.INVISIBLE);
			return;
		}
		super.onBackPressed();
	}

}
