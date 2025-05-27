package com.rubeusufv.sync.Tools.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.rubeusufv.sync.Core.SyncApplication;
import com.rubeusufv.sync.Exceptions.DatabaseException;
import com.rubeusufv.sync.Exceptions.SingletonViolationException;

import java.util.ArrayList;
import java.util.List;

public final class SQLiteDatabaseAdapter implements DatabaseContract {
    private static SQLiteDatabaseAdapter instance;
    protected SQLiteDatabase db;
    private final String DATABASE_NAME = "sync";
    private final String[] DATABASE_CREATION_SCRIPT = new String[] {
        "CREATE TABLE Event (eventId INTEGER PRIMARY KEY AUTOINCREMENT," +
        "title TEXT NOT NULL, date DATE);"
    };

    private SQLiteDatabaseAdapter() {
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

    public static SQLiteDatabaseAdapter createInstance(
    ) throws SingletonViolationException {
        if (instance != null) throw new SingletonViolationException();
        return instance = new SQLiteDatabaseAdapter();
    }

    public static SQLiteDatabaseAdapter getInstance() {
        return instance;
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
