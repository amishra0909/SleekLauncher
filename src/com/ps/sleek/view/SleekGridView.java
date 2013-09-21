package com.ps.sleek.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import com.ps.sleek.utils.DimensionUtils;

public class SleekGridView extends GridView {
	
	private static final int NUM_COLUMNS = 5;
	
	private Context context;

	public SleekGridView(Context context) {
		super(context);
		init(context);
	}

	public SleekGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context) {
		this.context = context;
		
		setNumColumns(NUM_COLUMNS);
		setColumnWidth(getColumnPixels(context));
	}

	public static int getColumnPixels(Context context) {
		return (DimensionUtils.getWidthPixels(context) - (NUM_COLUMNS + 1) * DimensionUtils.getPixelForDp(context, 5))/NUM_COLUMNS;
	}
}
