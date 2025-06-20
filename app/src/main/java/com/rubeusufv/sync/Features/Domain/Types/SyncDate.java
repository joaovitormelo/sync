package com.rubeusufv.sync.Features.Domain.Types;

import android.annotation.SuppressLint;

import java.io.Serializable;

public class SyncDate implements Serializable, Comparable<SyncDate> {
    private int day;
    private int month;
    private int year;

    public SyncDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int compareTo(SyncDate o) {
        if (getYear() < o.getYear()) return -1;
        else if (getYear() > o.getYear()) return 1;
        else if (getMonth() < o.getMonth()) return -1;
        else if (getMonth() > o.getMonth()) return 1;
        return getDay() - o.getDay();
    }

    @SuppressLint("DefaultLocale")
    public String toString() {
        // YYYY-MM-DD in SQL
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    public static SyncDate fromString(String str) {
        if (str == null || !str.matches("\\d{4}-\\d{2}-\\d{2}")) return null;

        String[] parts = str.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);

        return new SyncDate(day, month, year);
    }

    // tive que adicionar essa parte do codigo  para usar SyncDate como chave de HashMap corretamente no meu Calendar
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        SyncDate other = (SyncDate) obj;
        return day == other.day && month == other.month && year == other.year;
    }

    @Override
    public int hashCode() {
        int result = day;
        result = 31 * result + month;
        result = 31 * result + year;
        return result;
    }
}
