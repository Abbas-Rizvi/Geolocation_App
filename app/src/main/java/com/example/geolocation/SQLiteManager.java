package com.example.geolocation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class SQLiteManager extends SQLiteOpenHelper {


        private static SQLiteManager sqLiteManager;
        private static SQLiteDatabase db;
        private static final String DATABASE_NAME = "GEOCODES";
        private static final int DATABASE_VERSION = 1;


        public static class LocationEntry implements BaseColumns {
            public static final String TABLE_NAME = "tbl_notes";
            public static final String COLUMN_NAME_ADDRESS = "address";
            public static final String COLUMN_NAME_LATITUDE = "latitude";
            public static final String COLUMN_NAME_LONGITUDE = "longitude";

        }

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + LocationEntry.TABLE_NAME;

        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + LocationEntry.TABLE_NAME + " (" +
                        LocationEntry._ID + " INTEGER PRIMARY KEY," +
                        LocationEntry.COLUMN_NAME_ADDRESS + " TEXT," +
                        LocationEntry.COLUMN_NAME_LATITUDE + " REAL," +
                        LocationEntry.COLUMN_NAME_LONGITUDE + " REAL)";




        public SQLiteManager(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(SQL_CREATE_ENTRIES);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);

        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }


        // add location to database
        // -------------------------
        public void addDatbabaseLocation(Location location){
            SQLiteDatabase db = this.getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(LocationEntry.COLUMN_NAME_ADDRESS, location.getAddress());
            values.put(LocationEntry.COLUMN_NAME_LATITUDE, location.getLatitude());
            values.put(LocationEntry.COLUMN_NAME_LONGITUDE, location.getLongitude());

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(LocationEntry.TABLE_NAME, null, values);


        }

        public void open() throws SQLException {
            db = this.getWritableDatabase();
//        db = sqLiteManager.getWritableDatabase();

        }

        // get all locations from database
        // -------------------------
        public Cursor getAllLocations() {
            return db.query(LocationEntry.TABLE_NAME, null, null, null, null, null, null);
        }

        // update a row in database
        public void updateDatabaseLocation(Location location){
            SQLiteDatabase db = this.getWritableDatabase();


            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(LocationEntry.COLUMN_NAME_ADDRESS, location.getAddress());
            values.put(LocationEntry.COLUMN_NAME_LATITUDE, location.getLatitude());
            values.put(LocationEntry.COLUMN_NAME_LONGITUDE, location.getLongitude());

            // create a where statement
            String sqlWhere = LocationEntry._ID + " = ?";
            String[] sqlArg = {String.valueOf(location.getId())};


            // update Row (execute SQL)
            long newRowId = db.update(LocationEntry.TABLE_NAME, values, sqlWhere,sqlArg);

        }

    public int deleteLocation(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        String sqlWhere = LocationEntry._ID + " = ?";

        String[] sqlArg = {String.valueOf(id)};

        return db.delete(LocationEntry.TABLE_NAME, sqlWhere, sqlArg);
    }

}


