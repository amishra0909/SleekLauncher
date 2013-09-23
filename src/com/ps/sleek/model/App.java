package com.ps.sleek.model;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class App implements Parcelable {

	public String name;

	public String packageName;

	public Intent intent;

	public Drawable icon;

	public static class Columns {
		public static final String NAME = "name";
		public static final String PACKAGE = "package";
	}

	public final void setActivity(ComponentName className, int launchFlags) {
		intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.setComponent(className);
		intent.setFlags(launchFlags);
	}
	
	public App() {}

	public App(Parcel in) {
		this.name = in.readString();
		this.packageName = in.readString();
		this.intent = in.readParcelable(Intent.class.getClassLoader());
		this.icon = bitmapToDrawable((Bitmap) in.readParcelable(Bitmap.class.getClassLoader()));
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(packageName);
		dest.writeParcelable(intent, flags);
		dest.writeParcelable(drawableToBitmap(icon), flags);
	}

	public static final Parcelable.Creator<App> CREATOR = new Parcelable.Creator<App>() {
		public App createFromParcel(Parcel in) {
			return new App(in);
		}

		public App[] newArray(int size) {
			return new App[size];
		}
	};

	private Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable == null)
			return null;

		return ((BitmapDrawable) drawable).getBitmap();
	}

	private Drawable bitmapToDrawable(Bitmap bitmap) {
		if (bitmap == null)
			return null;

		return new BitmapDrawable(bitmap);
	}
}
