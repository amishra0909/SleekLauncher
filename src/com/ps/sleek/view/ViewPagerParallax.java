package com.ps.sleek.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.ps.sleek.manager.BackgroundManager;
import com.ps.sleek.model.Background;

public class ViewPagerParallax extends ViewPager {

	private Rect src = new Rect(), dst = new Rect();
	private int current_position = -1;
	private float current_offset = 0.0f;

	private Background background;
	private Context context;

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
		background = BackgroundManager.getInstance(context).getBackground();
	}

	@Override
	protected void onPageScrolled(int position, float offset, int offsetPixels) {
		super.onPageScrolled(position, offset, offsetPixels);
		current_position = position;
		current_offset = offset;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (current_position == -1) {
			current_position = getCurrentItem();
		}
		
		src.set((int) (background.overlapLevel * (current_position + current_offset)),
				0,
				(int) (background.overlapLevel * (current_position + current_offset) + (getWidth() * background.zoomLevel)),
				background.height);

		dst.set((int) (getScrollX()),
				0,
				(int) (getScrollX() + canvas.getWidth()),
				canvas.getHeight());

		canvas.drawBitmap(background.bitmap, src, dst, null);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}

	@Override
	public void setCurrentItem(int item) {
		super.setCurrentItem(item);
		current_position = item;
	}
}
