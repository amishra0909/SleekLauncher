package com.ps.sleek.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.ps.sleek.manager.AppsManager;

/**
 * Receives notifications when applications are added/removed.
 */
public class AppsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AppsManager.getInstance(context).loadApplications();
    }
    
    public void register(Context context) {
    	IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
		filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
		filter.addDataScheme("package");
		context.registerReceiver(this, filter);
    }
}
