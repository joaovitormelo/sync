package com.rubeusufv.sync.Features.Domain.Utils;

import com.rubeusufv.sync.Features.Domain.Types.SyncDate;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateParser {
    // Custom abbreviated months in Portuguese with dots
    private static String[] shortMonths = {
            "JAN.", "FEV.", "MAR.", "ABR.", "MAI.", "JUN.",
            "JUL.", "AGO.", "SET.", "OUT.", "NOV.", "DEZ."
    };

    public static Date fromSyncDate(SyncDate syncDate) {
        if (syncDate == null) return null;
        Date date = new Date();
        date.setDate(syncDate.getDay());
        date.setMonth(syncDate.getMonth() - 1);
        date.setYear(syncDate.getYear() - 1900);
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return date;
    }

    public static String formatDateForDatePicker(Date date) {
        if (date == null) return null;

        DateFormatSymbols symbols = new DateFormatSymbols(new Locale("pt", "BR"));
        symbols.setShortMonths(shortMonths);

        SimpleDateFormat sdf = new SimpleDateFormat("d 'DE' MMM 'DE' yyyy", symbols);
        return sdf.format(date).toUpperCase();
    }

    public static SyncDate dateToSyncDate(Date date) {
        return new SyncDate(date.getDate(), date.getMonth() + 1, date.getYear() + 1900);
    }

    public static int[] extractHourAndMinute(String timeStr) {
        String[] parts = timeStr.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        return new int[] { hour, minute };
    }
}
