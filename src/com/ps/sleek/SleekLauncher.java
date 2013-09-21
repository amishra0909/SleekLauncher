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

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.GridView;

import com.ps.sleek.adapter.ApplicationAdapter;
import com.ps.sleek.broadcastreceivers.ApplicationReceiver;
import com.ps.sleek.manager.ApplicationManager;
import com.ps.sleek.model.ClippedDrawable;

public class SleekLauncher extends Activity implements OnClickListener {

	private static final String LOG_TAG = "SleekLauncher";

	/**
	 * Keys during freeze/thaw.
	 */
	private static final String KEY_SAVE_GRID_OPENED = "grid.opened";

	private static boolean mWallpaperChecked;

	private final ApplicationReceiver mApplicationsReceiver = new ApplicationReceiver();

	private GridView mGrid;

	private LayoutAnimationController mShowLayoutAnimation;
	private LayoutAnimationController mHideLayoutAnimation;

	private boolean mBlockAnimation;

	private View mShowApplications;

	// private ApplicationsStackLayout mApplicationsStack;

	private Animation mGridEntry;
	private Animation mGridExit;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);

		setContentView(R.layout.launcher);

		registerIntentReceivers();

		bindApplications();
		// bindFavorites(true);
		// bindRecents();
		bindButtons();

		mGridEntry = AnimationUtils.loadAnimation(this, R.anim.grid_entry);
		mGridExit = AnimationUtils.loadAnimation(this, R.anim.grid_exit);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		// Close the menu
		if (Intent.ACTION_MAIN.equals(intent.getAction())) {
			getWindow().closeAllPanels();
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

	@Override
	protected void onRestoreInstanceState(Bundle state) {
		super.onRestoreInstanceState(state);
		final boolean opened = state.getBoolean(KEY_SAVE_GRID_OPENED, false);
		if (opened) {
			showApplications(false);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(KEY_SAVE_GRID_OPENED,
				mGrid.getVisibility() == View.VISIBLE);
	}

	private void registerIntentReceivers() {
		mApplicationsReceiver.register(this);
	}

	/**
	 * Creates a new appplications adapter for the grid view and registers it.
	 */
	private void bindApplications() {
		if (mGrid == null) {
			mGrid = (GridView) findViewById(R.id.all_apps);
		}
		ApplicationAdapter adapter = new ApplicationAdapter(this);
		mGrid.setAdapter(adapter);
		mGrid.setSelection(0);
		mGrid.setOnItemClickListener(adapter);
	}

	private void bindButtons() {
		mShowApplications = findViewById(R.id.app_button);
		mShowApplications.setOnClickListener(this);
	}

	private void showApplications(boolean animate) {
		if (mBlockAnimation) {
			return;
		}
		mBlockAnimation = true;

		if (mShowLayoutAnimation == null) {
			mShowLayoutAnimation = AnimationUtils.loadLayoutAnimation(this,
					R.anim.show_applications);
		}

		// This enables a layout animation; if you uncomment this code, you need
		// to
		// comment the line mGrid.startAnimation() below
		// mGrid.setLayoutAnimationListener(new ShowGrid());
		// mGrid.setLayoutAnimation(mShowLayoutAnimation);
		// mGrid.startLayoutAnimation();

		if (animate) {
			mGridEntry.setAnimationListener(new ShowGrid());
			mGrid.startAnimation(mGridEntry);
		}

		mGrid.setVisibility(View.VISIBLE);

		if (!animate) {
			mBlockAnimation = false;
		}

		// ViewDebug.startHierarchyTracing("Home", mGrid);
	}

	private void hideApplications() {
		if (mBlockAnimation) {
			return;
		}
		mBlockAnimation = true;

		if (mHideLayoutAnimation == null) {
			mHideLayoutAnimation = AnimationUtils.loadLayoutAnimation(this,
					R.anim.hide_applications);
		}

		mGridExit.setAnimationListener(new HideGrid());
		mGrid.startAnimation(mGridExit);
		mGrid.setVisibility(View.INVISIBLE);
		mShowApplications.requestFocus();

		// This enables a layout animation; if you uncomment this code, you need
		// to
		// comment the line mGrid.startAnimation() above
		// mGrid.setLayoutAnimationListener(new HideGrid());
		// mGrid.setLayoutAnimation(mHideLayoutAnimation);
		// mGrid.startLayoutAnimation();
	}

	private class HideGrid implements Animation.AnimationListener {
		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
			mBlockAnimation = false;
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}

	private class ShowGrid implements Animation.AnimationListener {
		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
			mBlockAnimation = false;
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}

	@Override
	public void onClick(View v) {
		if (mGrid.getVisibility() != View.VISIBLE) {
			showApplications(true);
		} else {
			hideApplications();
		}
	}

}
