package com.ps.sleek.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

public class DimensionUtils {
	
	public static int getWidthPixels(Context context) {
		return getDisplayMetrics(context).widthPixels;
	}
	
	public static int getHeightPixels(Context context) {
		return getDisplayMetrics(context).heightPixels;
	}
	
	public static int getPixelForDp(Context context, float dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getDisplayMetrics(context));
	}
	
	private static DisplayMetrics getDisplayMetrics(Context context) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}

}
