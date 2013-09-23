package com.ps.sleek.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import com.ps.sleek.utils.AppsLayoutUtils;

public class SleekGridView extends GridView {
	
	private static final int NUM_COLUMNS = 5;
	
	public SleekGridView(Context context) {
		super(context);
		init(context);
	}

	public SleekGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context) {
		setNumColumns(NUM_COLUMNS);
		setColumnWidth(AppsLayoutUtils.getColumnPixels(context));
		setVerticalSpacing(AppsLayoutUtils.getVerticalSpacing(context));
	}
}
