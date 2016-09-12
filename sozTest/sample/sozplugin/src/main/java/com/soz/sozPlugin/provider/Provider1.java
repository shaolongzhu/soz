package com.soz.sozPlugin.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.soz.log.Logger;
import com.soz.utils.ConstantUtils;

/**
 * Created by zhushaolong on 9/12/16.
 */
public class Provider1 extends ContentProvider {
    Logger mLogger = new Logger("Provider1");

    private static final Uri URI = Uri.parse("content://" + ConstantUtils.PROVIDER_AUTHORITY);

    private static final String TABLE = "person";
    private static final String NAME = "name";

    private DataBase mDb;

    @Override
    public boolean onCreate() {
        mDb = new DataBase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        mLogger.i("query");
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = mDb.getReadableDatabase();
        qb.setTables(TABLE);
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase sqLiteDatabase = mDb.getWritableDatabase();
        long rowId = sqLiteDatabase.insert(TABLE, "", values);
        mLogger.i("insert[rowId = " + rowId + "]");
        if (rowId > 0) {
            Uri rowUri = ContentUris.appendId(URI.buildUpon(), rowId).build();
            return rowUri;
        }
        throw new SQLiteException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private static class DataBase extends SQLiteOpenHelper {
        private static int VERSION = 1;
        private static final String DB_NAME = "persons.db";

        public DataBase(Context context) {
            super(context, DB_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("Create table " + TABLE + "( _id Integer PRIMARY KEY AUTOINCREMENT, " + NAME + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS + " + TABLE);
            onCreate(db);
        }
    }
}
