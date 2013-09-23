package com.ps.sleek.manager;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.ps.sleek.model.Background;
import com.ps.sleek.utils.AppsLayoutUtils;
import com.ps.sleek.utils.DimensionUtils;

public class BackgroundManager {
	
	private static BackgroundManager instance;
	
	private Background background;

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
	
	public Background getBackground() {
		if(background == null) {
			loadBackground();
		}
		return background;
	}
	
    public void loadBackground() {
    	Drawable wallpaper = WallpaperManager.getInstance(context).getDrawable();
		background = new Background(wallpaper);
    	background.height = wallpaper.getIntrinsicHeight();
		background.width = wallpaper.getIntrinsicWidth();

		int screenHeight = DimensionUtils.getHeightPixels(context);
		int screenWidth = DimensionUtils.getWidthPixels(context);
		background.zoomLevel = ((float) background.height) / screenHeight; // we are always in 'fitY' mode

		int sampleSize = Math.round(background.zoomLevel);

		if (sampleSize > 1) {
			background.height = background.height / sampleSize;
			background.width = background.width / sampleSize;
			background.zoomLevel = ((float) background.height) / screenHeight;
		}

		background.overlapLevel = background.zoomLevel * Math.min(Math.max(background.width / background.zoomLevel - screenWidth, 0)
						/ (AppsLayoutUtils.getNumPages(context) - 1),
						screenWidth / 2); // how many pixels to shift for each panel
    }
    
}
