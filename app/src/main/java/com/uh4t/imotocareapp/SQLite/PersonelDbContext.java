package com.uh4t.imotocareapp.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.uh4t.imotocareapp.Models.Personell;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class PersonelDbContext extends SQLiteOpenHelper {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Personel";
        public static final String COLUMN_NAME_PersonId = "PersonID";
        public static final String COLUMN_NAME_GarageId= "GarageId";
        public static final String COLUMN_NAME_Fname = "Fname";
        public static final String COLUMN_NAME_Lname = "Lname";
        public static final String COLUMN_NAME_Contact = "Contact";
        public static final String COLUMN_NAME_Type = "Type";
        public static final String COLUMN_NAME_ImageUri = "ImageUrl";
        public static final String COLUMN_NAME_GarageName = "GarageName";
        public static final String COLUMN_NAME_Address = "Address";
        public static final String COLUMN_NAME_Longitude = "Longitude";
        public static final String COLUMN_NAME_Lattitude = "Lattitude";
        public static final String COLUMN_NAME_CreatedOn = "CreatedOn";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_PersonId + " TEXT," +
                    FeedEntry.COLUMN_NAME_GarageId + " TEXT," +
                    FeedEntry.COLUMN_NAME_Fname + " TEXT," +
                    FeedEntry.COLUMN_NAME_Lname + " TEXT," +
                    FeedEntry.COLUMN_NAME_Contact + " TEXT," +
                    FeedEntry.COLUMN_NAME_Type + " TEXT," +
                    FeedEntry.COLUMN_NAME_ImageUri + " TEXT," +
                    FeedEntry.COLUMN_NAME_GarageName + " TEXT," +
                    FeedEntry.COLUMN_NAME_Address + " TEXT," +
                    FeedEntry.COLUMN_NAME_Longitude + " DOUBLE," +
                    FeedEntry.COLUMN_NAME_Lattitude + " DOUBLE," +
                    FeedEntry.COLUMN_NAME_CreatedOn + " DATETIME)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public PersonelDbContext(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public Personell Insert(Personell _personel){
        // Gets the data repository in write mode

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_PersonId, _personel.getPersonId());
        values.put(FeedEntry.COLUMN_NAME_GarageId,  _personel.getGarageId());
        values.put(FeedEntry.COLUMN_NAME_Fname,  _personel.getFname());
        values.put(FeedEntry.COLUMN_NAME_Lname,  _personel.getLname());
        values.put(FeedEntry.COLUMN_NAME_Contact,  _personel.getContact());
        values.put(FeedEntry.COLUMN_NAME_Type,  _personel.getType());
        values.put(FeedEntry.COLUMN_NAME_ImageUri,  _personel.getImageUri());
        values.put(FeedEntry.COLUMN_NAME_GarageName,  _personel.getGarageName());
        values.put(FeedEntry.COLUMN_NAME_Address,  _personel.getAddress());
        values.put(FeedEntry.COLUMN_NAME_Longitude,  _personel.getLongitude());
        values.put(FeedEntry.COLUMN_NAME_Lattitude,  _personel.getLattitude());
        values.put(FeedEntry.COLUMN_NAME_CreatedOn,  new Date().toString());

        if (getPerson(_personel.getPersonId()) != null){
            return null;
        }

        long newRowId = db.insert(FeedEntry.TABLE_NAME, null, values);

        String[] allColumns = {
                BaseColumns._ID,
                FeedEntry.COLUMN_NAME_PersonId,
                FeedEntry.COLUMN_NAME_GarageId,
                FeedEntry.COLUMN_NAME_Fname,
                FeedEntry.COLUMN_NAME_Lname,
                FeedEntry.COLUMN_NAME_Contact,
                FeedEntry.COLUMN_NAME_Type,
                FeedEntry.COLUMN_NAME_ImageUri,
                FeedEntry.COLUMN_NAME_GarageName,
                FeedEntry.COLUMN_NAME_Address,
                FeedEntry.COLUMN_NAME_Longitude,
                FeedEntry.COLUMN_NAME_Lattitude
        };


        Cursor cursor = db.query(FeedEntry.TABLE_NAME,
                allColumns, BaseColumns._ID + " = " + newRowId, null,
                null, null, null);
        cursor.moveToFirst();
        Personell newPersonel = personellToComment(cursor);

        if (newRowId != 0)
            return newPersonel;
        else
            return null;
    }

    public List<Personell> getPeople(String lon, String lat){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Personell> _people = new ArrayList<Personell>();
        String[] projection = {
                BaseColumns._ID,
                FeedEntry.COLUMN_NAME_PersonId,
                FeedEntry.COLUMN_NAME_GarageId,
                FeedEntry.COLUMN_NAME_Fname,
                FeedEntry.COLUMN_NAME_Lname,
                FeedEntry.COLUMN_NAME_Contact,
                FeedEntry.COLUMN_NAME_Type,
                FeedEntry.COLUMN_NAME_ImageUri,
                FeedEntry.COLUMN_NAME_GarageName,
                FeedEntry.COLUMN_NAME_Address,
                FeedEntry.COLUMN_NAME_Longitude,
                FeedEntry.COLUMN_NAME_Lattitude,
                FeedEntry.COLUMN_NAME_CreatedOn
        };

// Filter results WHERE "title" = 'My Title'
        String selection = FeedEntry.COLUMN_NAME_Longitude + " = ? and " + FeedEntry.COLUMN_NAME_Lattitude + " = ?";
        String[] selectionArgs = { lon, lat };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedEntry.COLUMN_NAME_CreatedOn + " DESC";

        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Personell comment = personellToComment(cursor);
            Log.d(TAG, "get peronel = " + personellToComment(cursor).toString());
            _people.add(comment);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return _people;
    }

    public Personell getPerson(String personelId){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                FeedEntry.COLUMN_NAME_PersonId,
                FeedEntry.COLUMN_NAME_GarageId,
                FeedEntry.COLUMN_NAME_Fname,
                FeedEntry.COLUMN_NAME_Lname,
                FeedEntry.COLUMN_NAME_Contact,
                FeedEntry.COLUMN_NAME_Type,
                FeedEntry.COLUMN_NAME_ImageUri,
                FeedEntry.COLUMN_NAME_GarageName,
                FeedEntry.COLUMN_NAME_Address,
                FeedEntry.COLUMN_NAME_Longitude,
                FeedEntry.COLUMN_NAME_Lattitude
        };

// Filter results WHERE "title" = 'My Title'
        String selection = FeedEntry.COLUMN_NAME_PersonId + " = ?";
        String[] selectionArgs = { personelId };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedEntry.COLUMN_NAME_CreatedOn + " DESC";

        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        cursor.moveToFirst();
        Personell _personel = personellToComment(cursor);
        return  _personel;
    }
    private Personell personellToComment(Cursor cursor) {

        Personell _perons = new Personell(
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getString(9),
                cursor.getDouble(10),
                cursor.getDouble(11)
        );

        return _perons;
    }
}
