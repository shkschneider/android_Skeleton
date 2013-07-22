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
package me.shkschneider.skeleton.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import me.shkschneider.skeleton.SkeletonApplication;

// TODO: Develop further
public class SqliteHelper extends SQLiteOpenHelper {

    private static final String DATABASE = SkeletonApplication.TAG;
    private static final int VERSION = 1;

    private SQLiteDatabase mDatabase;

    public SqliteHelper(final Context context) {
        super(context, DATABASE, null, VERSION);
    }

    public SQLiteDatabase getDatabase() {
        return mDatabase;
    }

    @Override
    public void onCreate(final SQLiteDatabase database) {
        mDatabase = database;
        if (mDatabase == null) {
            return ;
        }

        mDatabase.execSQL("CREATE TABLE " + ExampleTable.NAME + "(" +
                ExampleTable.ExampleTableColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + ", " +
                ExampleTable.ExampleTableColumns.NAME + " TEXT " + ");");
    }

    @Override
    public void onUpgrade(final SQLiteDatabase database, final int oldVersion, final int newVersion) {
        if (database == null || oldVersion >= newVersion) {
            return ;
        }

        LogHelper.w("Upgrading database from v" + oldVersion + " to v" + newVersion);

        // Customize
        LogHelper.w("All data will be erased!");
        database.execSQL("DROP TABLE IF EXISTS " + ExampleTable.NAME);
        onCreate(database);
    }

    // Tables

    public static final class ExampleTable {

        public static final String NAME = "ExampleTable";

        private ExampleTable() {
        }

        // Columns

        public static final class ExampleTableColumns implements BaseColumns {

            public ExampleTableColumns() {
            }

            // Rows

            public static final String NAME = "row";

        }

    }

}
