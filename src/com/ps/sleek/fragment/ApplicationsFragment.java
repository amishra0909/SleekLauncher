package com.ps.sleek.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.ps.sleek.R;
import com.ps.sleek.adapter.ApplicationAdapter;
import com.ps.sleek.model.Application;
import com.ps.sleek.view.SleekGridView;

public class ApplicationsFragment extends Fragment {
	
	private static final String KEY_APPLICATIONS = "applications";
	private LayoutAnimationController mShowLayoutAnimation;
	private LayoutAnimationController mHideLayoutAnimation;

	private boolean mBlockAnimation;

	private SleekGridView mGrid;

	private Animation mGridEntry;
	private Animation mGridExit;
	private ArrayList<Application> applications;
	
	public static ApplicationsFragment getFragment(ArrayList<Application> applications) {
		ApplicationsFragment fragment = new ApplicationsFragment();
		fragment.setApplications(applications);
		return fragment;
	}
	
	private void setApplications(ArrayList<Application> applications) {
		this.applications = applications;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_applications, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		bindApplications();

		mGridEntry = AnimationUtils.loadAnimation(getActivity(), R.anim.grid_entry);
		mGridExit = AnimationUtils.loadAnimation(getActivity(), R.anim.grid_exit);
	}
	
	private void bindApplications() {
		mGrid = (SleekGridView) getView().findViewById(R.id.all_apps);
		ApplicationAdapter adapter = new ApplicationAdapter(getActivity(), applications);
		mGrid.setAdapter(adapter);
		mGrid.setSelection(0);
		mGrid.setOnItemClickListener(adapter);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList(KEY_APPLICATIONS, applications);
	}

	private void showApplications(boolean animate) {
		if (mBlockAnimation) {
			return;
		}
		mBlockAnimation = true;

		if (mShowLayoutAnimation == null) {
			mShowLayoutAnimation = AnimationUtils.loadLayoutAnimation(getActivity(),
					R.anim.show_applications);
		}

		// This enables a layout animation; if you uncomment this code, you need
		// to
		// comment the line mGrid.startAnimation() below
		// mGrid.setLayoutAnimationListener(new ShowGrid());
		// mGrid.setLayoutAnimation(mShowLayoutAnimation);
		// mGrid.startLayoutAnimation();

		if (animate) {
			mGridEntry.setAnimationListener(new ShowGrid());
			mGrid.startAnimation(mGridEntry);
		}

		mGrid.setVisibility(View.VISIBLE);

		if (!animate) {
			mBlockAnimation = false;
		}

		// ViewDebug.startHierarchyTracing("Home", mGrid);
	}

	private void hideApplications() {
		if (mBlockAnimation) {
			return;
		}
		mBlockAnimation = true;

		if (mHideLayoutAnimation == null) {
			mHideLayoutAnimation = AnimationUtils.loadLayoutAnimation(getActivity(),
					R.anim.hide_applications);
		}

		mGridExit.setAnimationListener(new HideGrid());
		mGrid.startAnimation(mGridExit);
		mGrid.setVisibility(View.INVISIBLE);
//		mShowApplications.requestFocus();

		// This enables a layout animation; if you uncomment this code, you need
		// to
		// comment the line mGrid.startAnimation() above
		// mGrid.setLayoutAnimationListener(new HideGrid());
		// mGrid.setLayoutAnimation(mHideLayoutAnimation);
		// mGrid.startLayoutAnimation();
	}
	
	private class HideGrid implements Animation.AnimationListener {
		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
			mBlockAnimation = false;
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}

	private class ShowGrid implements Animation.AnimationListener {
		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
			mBlockAnimation = false;
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}

}
