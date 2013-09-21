package com.ps.sleek.broadcastreceivers;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.ps.sleek.model.ClippedDrawable;

/**
 * Receives intents from other applications to change the wallpaper.
 */
public class WallpaperReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ((Activity) context).getWindow().setBackgroundDrawable(new ClippedDrawable(WallpaperManager.getInstance(context).getDrawable()));
    }
    
    public void register(Context context) {
    	IntentFilter filter = new IntentFilter(Intent.ACTION_WALLPAPER_CHANGED);
		context.registerReceiver(this, filter);
    }
}
