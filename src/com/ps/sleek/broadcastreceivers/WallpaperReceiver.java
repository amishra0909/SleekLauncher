package com.ps.sleek.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;

import com.ps.sleek.manager.BackgroundManager;

/**
 * Receives intents from other applications to change the wallpaper.
 */
public class WallpaperReceiver extends BroadcastReceiver {

	@Override
    public void onReceive(Context context, Intent intent) {
		BackgroundManager.getInstance(context).loadWallpaper();
    }
    
    public void register(Context context) {
    	IntentFilter filter = new IntentFilter(Intent.ACTION_WALLPAPER_CHANGED);
		context.registerReceiver(this, filter);
    }
    
    public void setOnWallpaperChangedListener(OnWallpaperChangedListener listener) {
    	listener.onWallpaperChanged();
    }
    
    public interface OnWallpaperChangedListener {
    	public void onWallpaperChanged();
    }
}
