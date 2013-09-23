package com.ps.sleek.manager;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class BackgroundManager {
	
	private static BackgroundManager instance;
	
	private Drawable wallpaper;

	private Context context;
	
	private BackgroundManager() {}
	
	private BackgroundManager(Context context) {
		this.context = context;
	}
	
	public static BackgroundManager getInstance(Context context) {
		if(instance == null) {
			instance = new BackgroundManager(context);
		}
		return instance;
	}
	
	public Drawable getWallpaper() {
		if(wallpaper == null) {
			Log.d("profile", "getting wallpaper");
			loadWallpaper();
		}
		return wallpaper;
	}
	
    public void loadWallpaper() {
    	wallpaper = WallpaperManager.getInstance(context).getDrawable();
    }
    
}
