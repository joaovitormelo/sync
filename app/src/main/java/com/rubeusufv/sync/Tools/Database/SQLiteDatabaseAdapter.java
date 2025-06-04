package com.rubeusufv.sync.Tools.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.rubeusufv.sync.Core.SyncApplication;
import com.rubeusufv.sync.Core.Exceptions.DatabaseException;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseAdapter implements DatabaseContract {
    protected SQLiteDatabase db;
    private final String DATABASE_NAME = "sync";
    private final String[] DATABASE_CREATION_SCRIPT = new String[] {
        "CREATE TABLE Event (eventId INTEGER PRIMARY KEY AUTOINCREMENT," +
        "title TEXT NOT NULL, date DATE);"
    };

    public SQLiteDatabaseAdapter() {
        Context ctx = SyncApplication.getAppContext();

        db = ctx.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        if (isDatabaseEmpty()) {
            createTables();
        }
    }

    private void createTables() {
        for (String script : DATABASE_CREATION_SCRIPT) {
            db.execSQL(script);
        }
    }

    private boolean isDatabaseEmpty() {
        ArrayList<DatabaseEntry> results = select(
            "sqlite_master", null, "type = 'table'", null,
                null
        );
        return results.size() == 1;
    }

    @Override
    public void insert(String table, List<String> values) throws DatabaseException {

    }

    @Override
    public ArrayList<DatabaseEntry> select(
        String table, List<String> fields, String where, List<String> whereArgs,
        String groupBy
    ) throws DatabaseException {
        return new ArrayList<>();
    }

    @Override
    public void update(String table, List<String> fields, List<String> values, String where, List<String> whereArgs) throws DatabaseException {

    }

    @Override
    public void delete(String table, String where, List<String> whereArgs) throws DatabaseException {

    }
}
