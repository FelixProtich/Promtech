package com.felix.promtech;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

import static android.os.Build.ID;

public class TasksProvider extends ContentProvider {

    static final String PROVIDER_NAME = "promtechtasks";
    static final String URL = "content://"+ PROVIDER_NAME +"/tasks";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String _ID = "_id";
    static final String TASK_NAME = "task_name";

    private static HashMap<String, String> TASKS_PROJECTION_MAP;

    static final int TASKS = 1;
    static final int TASKS_ID = 2;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "tasks", TASKS);
        uriMatcher.addURI(PROVIDER_NAME, "tasks/#", TASKS_ID);
    }

    private SQLiteDatabase db;
    static final String DB_NAME = "Promtech";
    static final String TASKS_TABLE_NAME = "tasks";
    static final int DB_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + TASKS_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " task_name TEXT NOT NULL, " +
                    " task_amount TEXT NOT NULL);";

    /**
     * Helper class that creates and manages provider's data repository
     */

    private static class DatabaseHelper extends SQLiteOpenHelper{

        DatabaseHelper(Context context){
            super(context, DB_NAME,null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            sqLiteDatabase.execSQL(CREATE_DB_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TASKS_TABLE_NAME);
            onCreate(sqLiteDatabase);

        }
    }


    public TasksProvider() {
    }

    @Override
    public boolean onCreate() {

        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        //create a writeable db
        db = dbHelper.getWritableDatabase();
        return db !=null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TASKS_TABLE_NAME);

        switch (uriMatcher.match(uri)){
            case TASKS:
                qb.setProjectionMap(TASKS_PROJECTION_MAP);
                break;
            case TASKS_ID:
                qb.appendWhere(_ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                //do nothing
        }

        //sorting

        if(sortOrder == null || sortOrder.equals("")){
            sortOrder = TASK_NAME;
        }

        Cursor c = qb.query(db, projection, selection,selectionArgs,
                null, null, null, null);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TASKS:
                return "vnd.android.cursor.dir/tasks";
            case TASKS_ID:
                return "vnd.android.cursor.item/tasks";
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId = db.insert(TASKS_TABLE_NAME, "",values);

        //check if row was added successfully
        if (rowId > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case TASKS:
                count = db.delete(TASKS_TABLE_NAME,selection, selectionArgs);
                break;
            case TASKS_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(TASKS_TABLE_NAME, ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ?
                                "AND ("+ selection +')' : ""), selectionArgs );
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return count;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case TASKS:
                count = db.update(TASKS_TABLE_NAME, values, selection, selectionArgs);
                break;
            case TASKS_ID:
                String id = uri.getPathSegments().get(1);
                count = db.update(TASKS_TABLE_NAME,values,
                        ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ?
                                "AND ("+ selection +')' : ""), selectionArgs );
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }
}