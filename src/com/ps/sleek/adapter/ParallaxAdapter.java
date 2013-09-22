package com.ps.sleek.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ps.sleek.fragment.LauncherFragment;

public class ParallaxAdapter extends FragmentStatePagerAdapter {

	public ParallaxAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		return new LauncherFragment();
	}

	@Override
	public int getCount() {
		return 3;
	}

}
