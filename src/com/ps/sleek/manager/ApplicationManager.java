package com.ps.sleek.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import com.ps.sleek.model.Application;
import com.ps.sleek.utils.ApplicationLayoutUtils;

public class ApplicationManager {
	
	private static ApplicationManager instance;
	
	private ArrayList<Application> applications;

	private Context context;
	
	private ApplicationManager() {}
	
	private ApplicationManager(Context context) {
		this.context = context;
		applications = new ArrayList<Application>();
	}
	
	public static ApplicationManager getInstance(Context context) {
		if(instance == null) {
			instance = new ApplicationManager(context);
		}
		return instance;
	}
	
	public void teardown() {
		/* Remove app callbacks */
        for (Application app : applications) {
            app.icon.setCallback(null);
        }
	}

	public ArrayList<Application> getApplications() {
		if(applications.isEmpty()) {
			loadApplications();
		}
		return applications;
	}
	
    /**
     * Loads the list of installed applications in mApplications.
     */
    public void loadApplications() {
        PackageManager manager = context.getPackageManager();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        final List<ResolveInfo> apps = manager.queryIntentActivities(mainIntent, 0);
        Collections.sort(apps, new ResolveInfo.DisplayNameComparator(manager));

        if (apps != null) {
            applications.clear();

            for (ResolveInfo app : apps) {
                Application application = new Application();

                application.name = app.loadLabel(manager).toString();
                application.setActivity(new ComponentName(
                        app.activityInfo.applicationInfo.packageName,
                        app.activityInfo.name),
                        Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                application.icon = app.activityInfo.loadIcon(manager);

                applications.add(application);
            }
        }
    }

	public ArrayList<Application> getApplicationsSet(int position) {
		int num = 5 * ApplicationLayoutUtils.getNumRows(context);
		int start = num * position;
		int end = Math.min(num * (position + 1), getApplications().size());
		return new ArrayList<Application>(getApplications().subList(start, end));
	}

}
