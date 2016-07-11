package com.antonioleiva.mvpexample.app.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.UnsupportedSchemeException;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;

/**
 * Created by ebpearls on 7/7/2016.
 */
public class MyContentProvider extends ContentProvider {
    // Use an int for each URI we will run, this represents the different queries
    private static final int COMPANY = 100;
    private static final int COMPANY_ID = 101;
    private static final int FOLDER = 200;
    private static final int FOLDER_ID = 201;

    //URIMatcher that will take in a URI and match it to the appropriate integer identifier we defined:
    private static final UriMatcher uriMatcher = buildUriMatcher();
    /*we can define our other class variables such as our SQLiteOpenHelper which is used to access the database itself,*/
    private MyDbHelper myDbHelper;

    @Override
    public boolean onCreate() {
        myDbHelper = new MyDbHelper(getContext());
        return true;
    }

    /**
     * Builds a UriMatcher that is used to determine witch database request is being made.
     */
    public static UriMatcher buildUriMatcher() {
        String content = Contract.CONTENT_AUTHORITY;
        // All paths to the UriMatcher have a corresponding code to return
        // when a match is found (the ints above).
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content, Contract.PATH_COMPANY, COMPANY);
        matcher.addURI(content, Contract.PATH_COMPANY + "/#", COMPANY_ID);
        matcher.addURI(content, Contract.PATH_FOLDERS, FOLDER);
        matcher.addURI(content, Contract.PATH_FOLDERS + "/#", FOLDER_ID);
        return matcher;
    }

    /**
     * The query method takes in five parameters:
     * In order to query the database, we will switch based on the matched URI integer and query the appropriate table as necessary.
     *
     * @param uri           The URI (or table) that should be queried.
     * @param projection    A string array of columns that will be returned in the result set.
     * @param selection     A string defining the criteria for results to be returned
     * @param selectionArgs Arguments to the above criteria that rows will be checked against.
     * @param sortOrder     A string of the column(s) and order to sort the result set by.
     * @return
     */
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = myDbHelper.getWritableDatabase();
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case COMPANY:
                cursor = db.query(Contract.Company.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case COMPANY_ID:
                long _id = ContentUris.parseId(uri);
                cursor = db.query(Contract.Company.TABLE_NAME,
                        projection,
                        Contract.Company._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder);
            case FOLDER:
                cursor = db.query(Contract.Folders.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            case FOLDER_ID:
                _id = ContentUris.parseId(uri);
                db.query(Contract.Folders.TABLE_NAME,
                        projection,
                        Contract.Folders._ID + " = ?",
                        new String[]{
                                String.valueOf(_id)
                        },
                        null,
                        null,
                        sortOrder);

            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
        // Set the notification URI for the cursor to the one passed into the function. This

        // causes the cursor to register a content observer to watch for changes that happen to

        // this URI and any of it's descendants. By descendants, we mean any URI that begins

        // with this path.

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case COMPANY:
                return Contract.Company.CONTENT_TYPE;
            case COMPANY_ID:
                return Contract.Company.CONTENT_ITEM_TYPE;
            case FOLDER:
                return Contract.Folders.CONTENT_TYPE;
            case FOLDER_ID:
                return Contract.Folders.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = myDbHelper.getWritableDatabase();
        Uri returnUri = null;
        long _id;
        switch (uriMatcher.match(uri)) {
            case COMPANY:
                //returns the row ID of the newly inserted row, or -1 if an error occurred
                _id = db.insert(Contract.Company.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = Contract.Company.buildCompanyUri(_id);
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into " + uri);
                }
            case FOLDER:
                _id = db.insert(Contract.Folders.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = Contract.Folders.buildFolderUri(_id);
                } else {
                    throw new UnsupportedOperationException("Unable to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = myDbHelper.getWritableDatabase();
        int rows; // Number of rows effected
        switch (uriMatcher.match(uri)) {
            case COMPANY:
                rows = db.delete(Contract.Company.TABLE_NAME, selection, selectionArgs);
                break;

            case FOLDER:
                rows = db.delete(Contract.Folders.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        // Because null could delete all rows:

        if (selection == null || rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rows;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = myDbHelper.getWritableDatabase();
        int rows;
        switch (uriMatcher.match(uri)) {
            case COMPANY:
                rows = db.update(Contract.Company.TABLE_NAME, values, selection, selectionArgs);
                break;

            case FOLDER:
                rows = db.update(Contract.Folders.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }


        if (rows != 0) {

            getContext().getContentResolver().notifyChange(uri, null);

        }


        return rows;
    }
}
