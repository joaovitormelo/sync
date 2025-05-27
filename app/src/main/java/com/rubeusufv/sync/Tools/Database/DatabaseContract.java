package com.rubeusufv.sync.Tools.Database;

import com.rubeusufv.sync.Exceptions.DatabaseException;

import java.util.List;

public interface DatabaseContract {
    void insert(String table, List<String> values) throws DatabaseException;
    List<DatabaseEntry> select(
        String table, List<String> fields, String where, List<String> whereArgs
    ) throws DatabaseException;
    void update(
            String table, List<String> fields, List<String> values,
            String where, List<String> whereArgs
    ) throws DatabaseException;
    void delete(
            String table, String where, List<String> whereArgs
    ) throws DatabaseException;
}
