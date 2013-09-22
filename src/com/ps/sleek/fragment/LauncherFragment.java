package com.ps.sleek.fragment;

import android.app.Fragment;
import android.app.WallpaperManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.ps.sleek.R;
import com.ps.sleek.adapter.ApplicationAdapter;
import com.ps.sleek.view.SleekGridView;

public class LauncherFragment extends Fragment implements OnClickListener {
	
	/**
	 * Keys during freeze/thaw.
	 */
	private static final String KEY_SAVE_GRID_OPENED = "grid.opened";
	
	private LayoutAnimationController mShowLayoutAnimation;
	private LayoutAnimationController mHideLayoutAnimation;

	private boolean mBlockAnimation;

	private View mShowApplications;
	
	private SleekGridView mGrid;

	private Animation mGridEntry;
	private Animation mGridExit;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_launcher, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		bindApplications();
		bindButtons();

		mGridEntry = AnimationUtils.loadAnimation(getActivity(), R.anim.grid_entry);
		mGridExit = AnimationUtils.loadAnimation(getActivity(), R.anim.grid_exit);
	}
	
	private void bindApplications() {
		if (mGrid == null) {
			mGrid = (SleekGridView) getView().findViewById(R.id.all_apps);
		}
		ApplicationAdapter adapter = new ApplicationAdapter(getActivity());
		mGrid.setAdapter(adapter);
		mGrid.setSelection(0);
		mGrid.setOnItemClickListener(adapter);
	}

	private void bindButtons() {
		mShowApplications = getView().findViewById(R.id.app_button);
		mShowApplications.setOnClickListener(this);
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
		mShowApplications.requestFocus();

		// This enables a layout animation; if you uncomment this code, you need
		// to
		// comment the line mGrid.startAnimation() above
		// mGrid.setLayoutAnimationListener(new HideGrid());
		// mGrid.setLayoutAnimation(mHideLayoutAnimation);
		// mGrid.startLayoutAnimation();
	}
	
	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		if(savedInstanceState == null) {
			return;
		}
		final boolean opened = savedInstanceState.getBoolean(KEY_SAVE_GRID_OPENED, false);
		if (opened) {
			showApplications(false);
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(KEY_SAVE_GRID_OPENED,
				mGrid.getVisibility() == View.VISIBLE);
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

	@Override
	public void onClick(View v) {
		if (mGrid.getVisibility() != View.VISIBLE) {
			showApplications(true);
		} else {
			hideApplications();
		}
	}

	public boolean onBackPressed() {
		if (mGrid.getVisibility() == View.VISIBLE) {
			hideApplications();
			return true;
		}
		return false;
	}

}
