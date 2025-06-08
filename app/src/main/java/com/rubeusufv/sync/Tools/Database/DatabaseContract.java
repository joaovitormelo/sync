package com.rubeusufv.sync.Tools.Database;

import android.content.ContentValues;

import com.rubeusufv.sync.Core.Exceptions.DatabaseException;

import java.util.List;

public interface DatabaseContract {
    void insert(String table, ContentValues values) throws DatabaseException;

    List<DatabaseEntry> select(
        String table, String[] columns, String where, String[] whereArgs,
        String groupBy, String orderBy
    ) throws DatabaseException;

    void update(String table, ContentValues values, String where, String[] whereArgs) throws DatabaseException;

    void delete(String table, String where, String[] whereArgs) throws DatabaseException;
}
