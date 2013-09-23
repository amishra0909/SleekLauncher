package com.ps.sleek.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ps.sleek.fragment.ApplicationsFragment;
import com.ps.sleek.manager.ApplicationManager;
import com.ps.sleek.utils.ApplicationLayoutUtils;

public class ParallaxAdapter extends FragmentStatePagerAdapter {

	private Context context;
	private ApplicationManager applicationManager;

	public ParallaxAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.context = context;
		applicationManager = ApplicationManager.getInstance(context);
	}

	@Override
	public Fragment getItem(int position) {
		return ApplicationsFragment.getFragment(applicationManager.getApplicationsSet(position));
	}

	@Override
	public int getCount() {
		return applicationManager.getApplications().size() / (5 * ApplicationLayoutUtils.getNumRows(context));
	}

}
