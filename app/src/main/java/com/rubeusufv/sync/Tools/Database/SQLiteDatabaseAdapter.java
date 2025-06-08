package com.rubeusufv.sync.Tools.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rubeusufv.sync.Core.SyncApplication;
import com.rubeusufv.sync.Core.Exceptions.DatabaseException;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseAdapter implements DatabaseContract {
    protected SQLiteDatabase db;
    private final String DATABASE_NAME = "sync";

    private static SQLiteDatabaseAdapter INSTANCE;

    private final String[] DATABASE_CREATION_SCRIPT = new String[] {
            "CREATE TABLE User (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "tokenRubeus TEXT, " +
                    "idRubeus INTEGER, " +
                    "idGoogle INTEGER, " +
                    "name TEXT NOT NULL, " +
                    "password TEXT NOT NULL);",

            "CREATE TABLE Event (" +
                    "eventId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "userId INTEGER NOT NULL, " +
                    "title TEXT NOT NULL, " +
                    "description TEXT, " +
                    "syncDate TEXT, " +
                    "startHour TEXT, " +
                    "endHour TEXT, " +
                    "allDay BOOLEAN, " +
                    "color TEXT, " +
                    "category TEXT, " +
                    "rubeusImported BOOLEAN, " +
                    "rubeusId INTEGER, " +
                    "contactType TEXT, " +
                    "googleImported BOOLEAN, " +
                    "googleId INTEGER, " +
                    "FOREIGN KEY (userId) REFERENCES User(id));"
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
                null, null
        );
        return results.size() == 1;
    }

    @Override
    public void insert(String table, ContentValues values) throws DatabaseException {
        db.insert(table, null, values);
    }

    @Override
    public ArrayList<DatabaseEntry> select(
            String table, String[] columns, String where, String[] whereArgs, String groupBy, String orderBy
    ) throws DatabaseException {
        ArrayList<DatabaseEntry> result = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    table,               // tabela
                    columns,         // colunas
                    where,               // cláusula WHERE
                    whereArgs,           // argumentos para WHERE
                    groupBy,             // GROUP BY
                    null,                // HAVING
                    orderBy              // ORDER BY
            );

            Log.i("DATABASE", "Do a select and returned [" + cursor.getCount() + "] registers.");

            while (cursor.moveToNext()) {
                DatabaseEntry entry = DatabaseEntry.fromCursor(cursor);
                result.add(entry);
            }

        } catch (Exception e) {
            throw new DatabaseException("Select failed", "Something went wrong in table: " + table);
        } finally {
            if (cursor != null) cursor.close();
        }
        return result;
    }

    @Override
    public void update(String table, ContentValues values, String where, String[] whereArgs) throws DatabaseException {
        int count = db.update(table, values, where, whereArgs);
        Log.i("DATABASE", "Updated [" + count + "] registers");
    }

    @Override
    public void delete(String table, String where, String[] whereArgs) throws DatabaseException {
        int count = db.delete(table, where, whereArgs);
        Log.i("DATABASE", "Deleted [" + count + "] registers");
    }

    private void open() {
        Context ctx = SyncApplication.getAppContext();
        if(!db.isOpen()){
            db = ctx.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            Log.i("BANCO_DADOS", "Abriu conexão com o banco.");
        }else{
            Log.i("BANCO_DADOS", "Conexão com o banco já estava aberta.");
        }
    }

    public static SQLiteDatabaseAdapter getInstance(){
        if(INSTANCE == null)
            INSTANCE = new SQLiteDatabaseAdapter();
        INSTANCE.open();
        return INSTANCE;
    }

    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
            Log.i("BANCO_DADOS", "Fechou conexão com o Banco.");
        }
    }

}
