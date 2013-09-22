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

import android.app.Activity;
import android.os.Bundle;

import com.ps.sleek.broadcastreceivers.ApplicationReceiver;
import com.ps.sleek.fragment.LauncherFragment;
import com.ps.sleek.manager.ApplicationManager;

public class SleekLauncher extends Activity {

	private final ApplicationReceiver mApplicationsReceiver = new ApplicationReceiver();
	private LauncherFragment fragment;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
		
		setContentView(R.layout.activity_launcher);
		
		fragment = (LauncherFragment) getFragmentManager().findFragmentById(R.id.launcher_fragment);
		
		registerIntentReceivers();
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
	public void onBackPressed() {
		if(fragment.onBackPressed()) {
			return;
		}
		super.onBackPressed();
	}

}
