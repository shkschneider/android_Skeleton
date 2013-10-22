/**
 * Copyright 2013 ShkSchneider
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.shkschneider.skeleton.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import me.shkschneider.skeleton.android.LogHelper;

@SuppressWarnings("unused")
public class SqliteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "skeleton.db";

    public static final String TABLE_SKELETON = "skeleton";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ID_ = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_TEXT_ = "TEXT NOT NULL";

    public static final String[] TABLES = new String[] {
            TABLE_SKELETON
    };

    public SqliteHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase database) {
        database.execSQL(String.format("CREATE TABLE %s (%s %s, %s %s);",
                TABLE_SKELETON,
                COLUMN_ID, COLUMN_ID_,
                COLUMN_TEXT, COLUMN_TEXT_));
    }

    public Integer count(final String table) {
        final SQLiteDatabase database = getReadableDatabase();
        if (database == null) {
            LogHelper.w("SQLiteDatabase was NULL");
            return 0;
        }

        if (TextUtils.isEmpty(table)) {
            LogHelper.w("Table was NULL");
            return 0;
        }

        final Cursor cursor = database.rawQuery(String.format("SELECT %s FROM %s", COLUMN_ID, TABLE_SKELETON), null);
        cursor.close();
        return cursor.getCount();
    }

    public void add(final String table, final Column column) {
        final SQLiteDatabase database = getWritableDatabase();
        if (database == null) {
            LogHelper.w("SQLiteDatabase was NULL");
            return ;
        }

        if (TextUtils.isEmpty(table)) {
            LogHelper.w("Table was NULL");
            return ;
        }

        if (column == null) {
            LogHelper.w("Column was NULL");
            return ;
        }

        final ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, column.id);
        contentValues.put(COLUMN_TEXT, column.text);
        database.insert(table, null, contentValues);
        database.close();
    }

    public Column get(final String table, final Integer id) {
        final SQLiteDatabase database = getReadableDatabase();
        if (database == null) {
            LogHelper.w("SQLiteDatabase was NULL");
            return null;
        }

        if (TextUtils.isEmpty(table)) {
            LogHelper.w("Table was NULL");
            return null;
        }

        final Cursor cursor = database.query(table, null, String.format("%s=?", COLUMN_ID), new String[] { String.valueOf(id) }, null, null, null, null);
        cursor.moveToFirst();
        final Column column = new Column(cursor.getInt(0), cursor.getString(1));
        cursor.close();
        return column;
    }

    public Boolean update(final String table, final Column column) {
        final SQLiteDatabase database = getWritableDatabase();
        if (database == null) {
            LogHelper.w("SQLiteDatabase was NULL");
            return false;
        }

        if (TextUtils.isEmpty(table)) {
            LogHelper.w("Table was NULL");
            return false;
        }

        if (column == null) {
            LogHelper.w("Column was NULL");
            return false;
        }

        final ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, column.id);
        contentValues.put(COLUMN_TEXT, column.text);
        database.update(table, contentValues, String.format("%s=?", COLUMN_ID), new String[] { String.valueOf(column.id) });
        database.close();
        return true;
    }

    public Boolean remove(final String table, final Integer id) {
        final SQLiteDatabase database = getWritableDatabase();
        if (database == null) {
            LogHelper.w("SQLiteDatabase was NULL");
            return false;
        }

        if (TextUtils.isEmpty(table)) {
            LogHelper.w("Table was NULL");
            return false;
        }

        database.delete(table, String.format("%s=?", COLUMN_ID), new String[] { String.valueOf(id) });
        database.close();
        return true;
    }

    @Override
    public void onUpgrade(final SQLiteDatabase database, final int oldVersion, final int newVersion) {
        LogHelper.w(String.format("Upgrading from v%d to v%d", oldVersion, newVersion));
        if (database == null) {
            LogHelper.w("SQLiteDatabase was NULL");
            return ;
        }

        for (final String table : TABLES) {
            database.execSQL("DROP TABLE IF EXISTS " + table);
        }
        onCreate(database);
    }

    public static class Column {

        public Integer id;
        public String text;

        Column(final Integer id, final String text) {
            this.id = id;
            this.text = text;
        }

    }

}
