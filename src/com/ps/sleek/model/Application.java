package com.ps.sleek.model;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;

public class Application {
	
	public String name;
	
	public String packageName;
	
	public Intent intent;
	
	public Drawable icon;
	
	public static class Columns {
		public static final String NAME = "name";
		public static final String PACKAGE = "package";
	}
	
	public final void setActivity(ComponentName className, int launchFlags) {
        intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(className);
        intent.setFlags(launchFlags);
    }

}
