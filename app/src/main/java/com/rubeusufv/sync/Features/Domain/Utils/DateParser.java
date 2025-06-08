package com.rubeusufv.sync.Features.Domain.Utils;

import com.rubeusufv.sync.Features.Domain.Types.SyncDate;

import java.util.Date;

public class DateParser {
    public static Date fromSyncDate(SyncDate syncDate) {
        if (syncDate == null) return null;
        Date date = new Date();
        date.setDate(syncDate.getDay());
        date.setMonth(syncDate.getMonth());
        date.setYear(syncDate.getYear());
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return date;
    }
}
