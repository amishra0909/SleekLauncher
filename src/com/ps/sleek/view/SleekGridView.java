package com.ps.sleek.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class SleekGridView extends GridView {
	
	public SleekGridView(Context context) {
		super(context);
		init();
	}

	public SleekGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init() {
		setupDimensions();
	}

	private void setupDimensions() {
		
	}

}
