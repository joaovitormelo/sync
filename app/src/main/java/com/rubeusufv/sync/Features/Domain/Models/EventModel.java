package com.rubeusufv.sync.Features.Domain.Models;

import android.content.ContentValues;

import com.rubeusufv.sync.Features.Domain.Types.SyncDate;
import com.rubeusufv.sync.Features.Domain.Types.Color;
import com.rubeusufv.sync.Features.Domain.Types.ContactType;
import com.rubeusufv.sync.Tools.Database.DatabaseEntry;

public class EventModel {
    private int id;
    private int userId;
    private String title;
    private String description;
    private SyncDate syncDate;
    private String startHour;
    private String endHour;
    private boolean allDay;
    private Color color;
    private String category;
    private boolean rubeusImported;
    private int rubeusId;
    private ContactType contactType;
    private boolean googleImported;
    private int googleId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRubeusId() {
        return rubeusId;
    }

    public void setRubeusId(int rubeusId) {
        this.rubeusId = rubeusId;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public int getGoogleId() {
        return googleId;
    }

    public void setGoogleId(int googleId) {
        this.googleId = googleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SyncDate getDate() {
        return syncDate;
    }

    public void setDate(SyncDate syncDate) {
        this.syncDate = syncDate;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isRubeusImported() {
        return rubeusImported;
    }

    public void setRubeusImported(boolean rubeusImported) {
        this.rubeusImported = rubeusImported;
    }

    public boolean isGoogleImported() {
        return googleImported;
    }

    public void setGoogleImported(boolean googleImported) {
        this.googleImported = googleImported;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public EventModel(){};

    public EventModel(
            int id, int userId, String title, String description, SyncDate syncDate, String startHour,
            String endHour, boolean allDay, Color color, String category,
            boolean rubeusImported, int rubeusId, ContactType contactType,
            boolean googleImported, int googleId
    ) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.syncDate = syncDate;
        this.startHour = startHour;
        this.endHour = endHour;
        this.allDay = allDay;
        this.color = color;
        this.category = category;
        this.rubeusImported = rubeusImported;
        this.rubeusId = rubeusId;
        this.contactType = contactType;
        this.googleImported = googleImported;
        this.googleId = googleId;
    }

    public EventModel(
            int userId, String title, String description, SyncDate syncDate, String startHour,
            String endHour, boolean allDay, Color color, String category,
            boolean rubeusImported, boolean googleImported
    ) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.syncDate = syncDate;
        this.endHour = endHour;
        this.startHour = startHour;
        this.allDay = allDay;
        this.color = color;
        this.category = category;
        this.rubeusImported = rubeusImported;
        this.googleImported = googleImported;
    }

    public static Color fromCategory(String selectedCategory) {
        switch (selectedCategory) {
            case "Prova":
                return Color.RED;
            case "Reunião":
                return Color.GREEN;
            case "Lazer":
                return Color.YELLOW;
            case "Trabalho":
                return Color.PURPLE;
            default:
                return Color.BLUE;
        }
    }

    public static EventModel fromDatabaseEntry(DatabaseEntry entry) {
        EventModel event = new EventModel();
        event.id = entry.getAsInteger("eventId"); // ou "id"
        event.userId = entry.getAsInteger("userId");
        event.title = entry.getAsString("title");
        event.description = entry.getAsString("description");
        event.startHour = entry.getAsString("startHour");
        event.endHour = entry.getAsString("endHour");
        event.allDay = Boolean.TRUE.equals(entry.getAsBoolean("allDay"));
        event.category = entry.getAsString("category");
        event.rubeusImported = Boolean.TRUE.equals(entry.getAsBoolean("rubeusImported"));
        event.rubeusId = entry.getAsInteger("rubeusId");
        event.googleImported = Boolean.TRUE.equals(entry.getAsBoolean("googleImported"));
        event.googleId = entry.getAsInteger("googleId");

        // Datas no formato "YYYY-MM-DD"
        event.syncDate = SyncDate.fromString(entry.getAsString("syncDate"));

        // Enums (assume que o valor no banco está igual ao nome do enum)
        String colorStr = entry.getAsString("color");
        if (colorStr != null) {
            try {
                event.color = Color.valueOf(colorStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                event.color = Color.GRAY; // padrão em caso de erro
            }
        }

        String contactStr = entry.getAsString("contactType");
        if (contactStr != null) {
            try {
                event.contactType = ContactType.valueOf(contactStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                event.contactType = ContactType.NONE;
            }
        }

        return event;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put("id", this.id);
        values.put("userId", this.userId);
        values.put("title", this.title);
        values.put("description", this.description);
        values.put("syncDate", this.syncDate.toString());
        values.put("startHour", this.startHour);
        values.put("endHour", this.endHour);
        values.put("allDay", this.allDay);
        values.put("color", this.color.toString());
        values.put("category", this.category);
        values.put("rubeusImported", this.rubeusImported);
        values.put("rubeusId", this.rubeusId);
        values.put("contactType", this.contactType.toString());
        values.put("googleImported", this.googleImported);
        values.put("googleId", this.googleId);

        return values;
    }


    public static EventModel getMock() {
        return new EventModel(
            1, "Evento 1", "Descrição", new SyncDate(07, 06, 2025), "09:00",
            "10:00",false, Color.BLUE, "A", true,
            false
        );
    }
}
