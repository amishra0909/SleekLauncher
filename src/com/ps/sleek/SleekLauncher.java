package com.ps.sleek;

import com.ps.sleek.manager.ApplicationManager;
import com.ps.sleek.manager.BackgroundManager;

import android.app.Application;

public class SleekLauncher extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		ApplicationManager.getInstance(getApplicationContext()).loadApplications();
		BackgroundManager.getInstance(getApplicationContext()).loadWallpaper();
	}

}
