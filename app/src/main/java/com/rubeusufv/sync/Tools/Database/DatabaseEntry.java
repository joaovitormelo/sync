package com.rubeusufv.sync.Tools.Database;

import android.database.Cursor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// Representa uma linha da tabela resultante do select
public class DatabaseEntry {
    private final Map<String, Object> data;

    public DatabaseEntry() {
        this.data = new HashMap<>();
    }

    public void put(String column, Object value) {
        data.put(column, value);
    }

    public Object get(String column) {
        return data.get(column);
    }

    public String getAsString(String column) {
        Object value = data.get(column);
        return value != null ? value.toString() : null;
    }

    public Integer getAsInteger(String column) {
        Object value = data.get(column);
        return value instanceof Number ? ((Number) value).intValue() : null;
    }

    public Boolean getAsBoolean(String column) {
        Object value = data.get(column);
        if (value instanceof Boolean) return (Boolean) value;
        if (value instanceof Number) return ((Number) value).intValue() != 0;
        if (value instanceof String) return Boolean.parseBoolean((String) value);
        return null;
    }

    public Set<String> keySet() {
        return data.keySet();
    }

    public static DatabaseEntry fromCursor(Cursor cursor) {
        DatabaseEntry entry = new DatabaseEntry();
        String[] columnNames = cursor.getColumnNames();

        for (String column : columnNames) {
            int index = cursor.getColumnIndexOrThrow(column);
            int type = cursor.getType(index);

            switch (type) {
                case Cursor.FIELD_TYPE_INTEGER:
                    entry.put(column, cursor.getInt(index));
                    break;
                case Cursor.FIELD_TYPE_FLOAT:
                    entry.put(column, cursor.getFloat(index));
                    break;
                case Cursor.FIELD_TYPE_STRING:
                    entry.put(column, cursor.getString(index));
                    break;
                case Cursor.FIELD_TYPE_BLOB:
                    entry.put(column, cursor.getBlob(index));
                    break;
                case Cursor.FIELD_TYPE_NULL:
                default:
                    entry.put(column, null);
                    break;
            }
        }

        return entry;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}

