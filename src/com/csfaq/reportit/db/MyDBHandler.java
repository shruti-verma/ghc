package com.csfaq.reportit.db;


import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "ReportIt.db";
	public static final String TABLE_COMPLIANTS = "Complaints";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_COMPLIANTNUM = "compliantnum";
	public static final String COLUMN_CATEGORY = "catgeory";
	public static final String COLUMN_STATUS = "status";
	public static final String COLUMN_USERNAME = "username";
	public static final String COLUMN_COMMENTS = "comments";
	public static final String COLUMN_LOCATION = "location";

	public MyDBHandler(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String CREATE_COMPLIANTS_TABLE = "CREATE TABLE " +
				TABLE_COMPLIANTS + "("
				+ COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_COMPLIANTNUM 
				+ " INTEGER," + COLUMN_CATEGORY+ " STRING," + COLUMN_STATUS+ " STRING," + COLUMN_USERNAME+ " STRING," 
				+ COLUMN_COMMENTS+ " STRING," +  COLUMN_LOCATION+ " STRING," + ")";

		db.execSQL(CREATE_COMPLIANTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPLIANTS);
		onCreate(db);
	}


}

