package com.rubeusufv.sync.Features.Data.EventsData;

import android.content.ContentValues;

import com.rubeusufv.sync.Features.Domain.Models.EventModel;
import com.rubeusufv.sync.Features.Domain.Models.UserModel;
import com.rubeusufv.sync.Features.Domain.Types.SyncDate;
import com.rubeusufv.sync.Tools.Database.DatabaseEntry;
import com.rubeusufv.sync.Tools.Database.SQLiteDatabaseAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class EventsData implements EventsDataContract {
    public ArrayList<EventModel> viewEvents(UserModel user, int year, int month) {
        // Cria uma data de início do mês
        SyncDate date = new SyncDate(1, month, year);

        // WHERE: eventos com syncDate igual à data fornecida e do usuário específico
        String where = "syncDate = ? AND userId = ?";
        String[] whereArgs = new String[]{ date.toString(), String.valueOf(user.getId()) };

        ArrayList<DatabaseEntry> result = SQLiteDatabaseAdapter.getInstance().select(
                "Event",
                new String[]{("*")},
                where,
                whereArgs,
                "",
                "startHour ASC" // ordena por horário de início
        );

        // Converte cada resultado em EventModel
        ArrayList<EventModel> events = new ArrayList<>();
        for (DatabaseEntry entry : result) {
            events.add(EventModel.fromDatabaseEntry(entry));
        }

        return events;
    }


    public EventModel createNewEvent(UserModel user, EventModel event){
        ContentValues values = event.toContentValues();
        SQLiteDatabaseAdapter.getInstance().insert("Event", values);

        return event;
    }

    public void updateEvent(UserModel user, EventModel event){
        ContentValues values = event.toContentValues();
        String where = "id = ?";
        String[] whereArgs = new String[]{String.valueOf(event.getId())};
        SQLiteDatabaseAdapter.getInstance().update("Event", values, where, whereArgs);
    }

    public void removeEvent(UserModel user, EventModel event){
        String where = "id = ?";
        String[] whereArgs = new String[]{String.valueOf(event.getId())};
        SQLiteDatabaseAdapter.getInstance().delete("Event", where, whereArgs);
    }

}
