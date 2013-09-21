package com.ps.sleek;


import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.ps.sleek.model.Application;
import com.xtreme.rest.DatabaseInitializer;
import com.xtreme.rest.utils.Logger;

public class SleekInitializer implements DatabaseInitializer {

	public static final class Tables {
        public static final String APPLICATIONS = "applications";
	}

	public static final class Views {
		public static final String APPLICATION = "application_view";
	}

    private static final String CREATE_TABLE_APPLICATION = "CREATE TABLE IF NOT EXISTS " + Tables.APPLICATIONS + " ( "
    	+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + Application.Columns.NAME + " TEXT, "
        + Application.Columns.PACKAGE + " TEXT, "
		+ "UNIQUE (" + Application.Columns.PACKAGE + ") ON CONFLICT REPLACE);";


//    private static final String CREATE_VIEW_WIDGET_ARTICLE_IMAGES = "CREATE VIEW IF NOT EXISTS " + Views.WIDGET_ARTICLE_IMAGES  + " AS "
//    	+ "SELECT * FROM (" + Tables.WIDGET_ARTICLES
//			+ " INNER JOIN " + Tables.ARTICLE_IMAGES + " ON " + Tables.WIDGET_ARTICLES + "." + Article.Columns.ARTICLE_ID + " = " + Tables.ARTICLE_IMAGES + "." + Article.Columns.ARTICLE_ID
//		+ ");";



	private final String[] SQL_EXEC_COMMANDS = new String[] {
    	CREATE_TABLE_APPLICATION,
	};

	@Override
	public void onCreate(final SQLiteDatabase db) {
		for (final String sql : SQL_EXEC_COMMANDS) {
			Logger.d("execSQL: %s", sql);
			db.execSQL(sql);
		}
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
		// TODO: handle upgrade
	}

	@Override
	public void onDowngrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
		// TODO handle downgrade
	}

}
