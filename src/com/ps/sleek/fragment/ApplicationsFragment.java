package com.ps.sleek.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ps.sleek.R;
import com.ps.sleek.adapter.ApplicationAdapter;
import com.ps.sleek.model.Application;
import com.ps.sleek.view.SleekGridView;

public class ApplicationsFragment extends Fragment {
	
	private static final String KEY_APPLICATIONS = "applications";

	private SleekGridView mGrid;

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

}
