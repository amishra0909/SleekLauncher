package com.ps.sleek.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Background {
    public final Drawable wallpaper;
    
    public Bitmap bitmap;
    public float zoomLevel;
    public float overlapLevel;
    public int width;
    public int height;

    public Background(Drawable wallpaper) {
        this.wallpaper = wallpaper;
        bitmap = drawableToBitmap(wallpaper);
    }
    
    private Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}
}
