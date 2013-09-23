package com.ps.sleek.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

public class DimensionUtils {

	public static int getWidthPixels(Context context) {
		DisplayMetrics metrics = getDisplayMetrics(context);
		return Math.min(metrics.widthPixels, metrics.heightPixels);
	}

	public static int getHeightPixels(Context context) {
		DisplayMetrics metrics = getDisplayMetrics(context);
		return Math.max(metrics.heightPixels, metrics.widthPixels) - getStatusBarHeight(context);
	}

	public static int getPixelForDp(Context context, float dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getDisplayMetrics(context));
	}

	public static int getPixelForSp(Context context, float sp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getDisplayMetrics(context));
	}

	private static DisplayMetrics getDisplayMetrics(Context context) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}

	private static int getStatusBarHeight(Context context) {
		int result = 0;
		Resources resources = context.getResources();
		int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = resources.getDimensionPixelSize(resourceId);
		}
		return result;
	}

}
