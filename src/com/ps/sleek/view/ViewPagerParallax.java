package com.ps.sleek.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Debug;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

import com.ps.sleek.manager.BackgroundManager;

public class ViewPagerParallax extends ViewPager {
    private int saved_width = -1;
    private int saved_height = -1;
    private boolean insufficientMemory = false;

    private int max_num_pages=3;
    private int imageHeight;
    private int imageWidth;
    private float zoom_level;
    private float overlap_level;
    private Rect src = new Rect(), dst = new Rect();

    private boolean loggable = true;
	private Drawable wallpaper;
	private Drawable savedWallpaper;
	private Bitmap saved_bitmap;
	private Context context;
    private final static String TAG = "ViewPagerParallax";

    public ViewPagerParallax(Context context) {
        super(context);
        this.context = context;
        loadWallpaper();
    }

	public ViewPagerParallax(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        loadWallpaper();
    }
	
	public void loadWallpaper() {
		long start = System.currentTimeMillis();
		wallpaper = BackgroundManager.getInstance(context).getWallpaper();
		long end = System.currentTimeMillis();
		Log.d("profile", "get Wallpaper:" + (end - start));
		
		start = System.currentTimeMillis();
		set_new_background();
		end = System.currentTimeMillis();
		Log.d("profile", "set Wallpaper:" + (end - start));
	}

    @SuppressLint("NewApi")
    private int sizeOf(Bitmap data) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return data.getRowBytes() * data.getHeight();
        } else {
            return data.getByteCount();
        }
    }

    private void set_new_background() {
        if (wallpaper == null)
            return;

        if (getWidth()==0 || getHeight()==0)
            return;

        if ((saved_height == getHeight()) && (saved_width == getWidth()) &&
                (savedWallpaper.getConstantState() == wallpaper.getConstantState()))
            return;

        saved_bitmap = drawableToBitmap(wallpaper);

        imageHeight = wallpaper.getIntrinsicHeight();
        imageWidth = wallpaper.getIntrinsicWidth();
        if (loggable) Log.v(TAG, "imageHeight=" + imageHeight + ", imageWidth=" + imageWidth);

        zoom_level = ((float) imageHeight) / getHeight();  // we are always in 'fitY' mode

        int sampleSize = Math.round(zoom_level);

        if (sampleSize > 1) {
            imageHeight = imageHeight / sampleSize;
            imageWidth = imageWidth / sampleSize;
        }
        if (loggable) Log.v(TAG, "imageHeight=" + imageHeight + ", imageWidth=" + imageWidth);

        double max = Runtime.getRuntime().maxMemory(); //the maximum memory the app can use
        double heapSize = Runtime.getRuntime().totalMemory(); //current heap size
        double heapRemaining = Runtime.getRuntime().freeMemory(); //amount available in heap
        double nativeUsage = Debug.getNativeHeapAllocatedSize();
        double remaining = max - (heapSize - heapRemaining) - nativeUsage;

        int freeMemory = (int)(remaining / 1024);
        int bitmap_size = imageHeight * imageWidth * 4 / 1024;
        if (loggable) Log.v(TAG, "freeMemory = " + freeMemory);
        if (loggable) Log.v(TAG, "calculated bitmap size = " + bitmap_size);
        if (bitmap_size > freeMemory / 5) {
            insufficientMemory = true;
            return; // we aren't going to use more than one fifth of free memory
        }

        zoom_level = ((float) imageHeight) / getHeight();  // we are always in 'fitY' mode
        overlap_level = zoom_level * Math.min(Math.max(imageWidth / zoom_level - getWidth(), 0) / (max_num_pages - 1), getWidth()/2); // how many pixels to shift for each panel

        if (loggable) Log.i(TAG, "real bitmap size = " + sizeOf(saved_bitmap) / 1024);
        if (loggable) Log.v(TAG, "saved_bitmap.getHeight()=" + saved_bitmap.getHeight() + ", saved_bitmap.getWidth()=" + saved_bitmap.getWidth());


        saved_height = getHeight();
        saved_width = getWidth();
        savedWallpaper = wallpaper;
    }
    int current_position=-1;
    float current_offset=0.0f;

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
        current_position = position;
        current_offset = offset;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!insufficientMemory) {
            if (current_position == -1)
                current_position=getCurrentItem();
            // maybe we could get the current position from the getScrollX instead?
            src.set((int) (overlap_level * (current_position + current_offset)), 0,
                    (int) (overlap_level * (current_position + current_offset) + (getWidth() * zoom_level)), imageHeight);

            dst.set((int) (getScrollX()), 0,
                    (int) (getScrollX() + canvas.getWidth()), canvas.getHeight());

            canvas.drawBitmap(saved_bitmap, src, dst, null);
        }
    }
    
    private Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap); 
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public void set_max_pages(int num_max_pages) {
        max_num_pages = num_max_pages;
        set_new_background();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!insufficientMemory)
            set_new_background();
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
        current_position = item;
    }

    protected void onDetachedFromWindow() {
        if (saved_bitmap != null) {
            saved_bitmap.recycle();
            saved_bitmap = null;
            wallpaper = null;
            savedWallpaper = null;
        }
        super.onDetachedFromWindow();
    }
}
