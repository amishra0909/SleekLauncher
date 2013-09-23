package com.ps.sleek;

import android.app.Application;
import android.os.AsyncTask;

import com.ps.sleek.manager.AppsManager;
import com.ps.sleek.manager.BackgroundManager;

public class SleekLauncher extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				AppsManager.getInstance(getApplicationContext()).loadApplications();
				return null;
			}
		};
		
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				BackgroundManager.getInstance(getApplicationContext()).loadBackground();
				return null;
			}
		};
	}
}
