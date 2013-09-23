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

package com.ps.sleek.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.ps.sleek.R;
import com.ps.sleek.adapter.ParallaxAdapter;
import com.ps.sleek.broadcastreceivers.AppsReceiver;
import com.ps.sleek.broadcastreceivers.WallpaperReceiver;
import com.ps.sleek.broadcastreceivers.WallpaperReceiver.OnWallpaperChangedListener;
import com.ps.sleek.manager.AppsManager;
import com.ps.sleek.view.ViewPagerParallax;

public class AppsActivity extends FragmentActivity implements OnWallpaperChangedListener {

	private final AppsReceiver mApplicationsReceiver = new AppsReceiver();
	private final WallpaperReceiver wallpaperReceiver = new WallpaperReceiver();
	
	private ViewPagerParallax viewPager;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		
		setContentView(R.layout.activity_apps);
		
		viewPager = (ViewPagerParallax) findViewById(R.id.pager);
		viewPager.setAdapter(new ParallaxAdapter(this, getSupportFragmentManager()));
		
		registerIntentReceivers();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();

		// Remove the callback for the cached drawables or we leak
		// the previous Home screen on orientation change
		AppsManager.getInstance(this).teardown();

		unregisterReceiver(mApplicationsReceiver);
		unregisterReceiver(wallpaperReceiver);
	}

	private void registerIntentReceivers() {
		mApplicationsReceiver.register(this);
		wallpaperReceiver.register(this);
		wallpaperReceiver.setOnWallpaperChangedListener(this);
	}

	public static void start(Activity activity) {
		activity.startActivity(new Intent(activity, AppsActivity.class));
		activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	@Override
	public void onWallpaperChanged() {
		viewPager.loadWallpaper();
	}
	
}
