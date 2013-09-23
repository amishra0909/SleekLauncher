package com.ps.sleek.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ps.sleek.fragment.AppsGridFragment;
import com.ps.sleek.manager.AppsManager;
import com.ps.sleek.utils.AppsLayoutUtils;

public class ParallaxAdapter extends FragmentStatePagerAdapter {

	private Context context;
	private AppsManager applicationManager;

	public ParallaxAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.context = context;
		applicationManager = AppsManager.getInstance(context);
	}

	@Override
	public Fragment getItem(int position) {
		return AppsGridFragment.getFragment(applicationManager.getApplicationsSet(position));
	}

	@Override
	public int getCount() {
		return AppsLayoutUtils.getNumPages(context);
	}

}
