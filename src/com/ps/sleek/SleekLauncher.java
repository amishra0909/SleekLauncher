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

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.ps.sleek.activity.ApplicationsActivity;

public class SleekLauncher extends FragmentActivity implements OnClickListener {

	private View mShowApplications;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		
		setContentView(R.layout.activity_launcher);
		
		bindButtons();
	}
	
	private void bindButtons() {
		mShowApplications = findViewById(R.id.app_button);
		mShowApplications.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		ApplicationsActivity.start(this);
	}
	
}
