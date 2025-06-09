package com.rubeusufv.sync.Features.Domain.Models;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.rubeusufv.sync.Core.Exceptions.ValidationException;
import com.rubeusufv.sync.Features.Domain.Types.SyncDate;
import com.rubeusufv.sync.Features.Domain.Types.Color;
import com.rubeusufv.sync.Features.Domain.Types.ContactType;
import com.rubeusufv.sync.Features.Domain.Utils.ValidationHelper;
import com.rubeusufv.sync.Tools.Database.DatabaseEntry;

import java.io.Serializable;

public class EventModel implements Serializable {
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

    protected EventModel(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        title = in.readString();
        description = in.readString();
        startHour = in.readString();
        endHour = in.readString();
        allDay = in.readByte() != 0;
        category = in.readString();
        rubeusImported = in.readByte() != 0;
        rubeusId = in.readInt();
        googleImported = in.readByte() != 0;
        googleId = in.readInt();
    }

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

    public static class CATEGORY {
        public static final String TEST = "Prova";
        public static final String REUNION = "Reunião";
        public static final String LEISURE = "Lazer";
        public static final String WORK = "Trabalho";
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

    public static Color getColorFromCategory(String selectedCategory) {
        if (selectedCategory == null) return Color.BLUE;
        switch (selectedCategory) {
            case CATEGORY.TEST:
                return Color.RED;
            case CATEGORY.REUNION:
                return Color.GREEN;
            case CATEGORY.LEISURE:
                return Color.YELLOW;
            case CATEGORY.WORK:
                return Color.PURPLE;
            default:
                return Color.BLUE;
        }
    }

    public static EventModel fromDatabaseEntry(DatabaseEntry entry) {
        EventModel event = new EventModel();
        // Tratamento para campos numéricos (evitando NullPointerException)Add commentMore actions
        event.id = entry.getAsInteger("eventId") != null ? entry.getAsInteger("eventId") : 0;
        event.userId = entry.getAsInteger("userId") != null ? entry.getAsInteger("userId") : 0;
        event.rubeusId = entry.getAsInteger("rubeusId") != null ? entry.getAsInteger("rubeusId") : 0;
        event.googleId = entry.getAsInteger("googleId") != null ? entry.getAsInteger("googleId") : 0;

        // Campos de texto com fallback para string vazia
        event.title = entry.getAsString("title") != null ? entry.getAsString("title") : "";
        event.description = entry.getAsString("description") != null ? entry.getAsString("description") : "";
        event.startHour = entry.getAsString("startHour") != null ? entry.getAsString("startHour") : "";
        event.endHour = entry.getAsString("endHour") != null ? entry.getAsString("endHour") : "";
        event.category = entry.getAsString("category") != null ? entry.getAsString("category") : "";

        // Booleanos com fallback para false
        event.allDay = entry.getAsBoolean("allDay") != null && entry.getAsBoolean("allDay");
        event.rubeusImported = entry.getAsBoolean("rubeusImported") != null && entry.getAsBoolean("rubeusImported");
        event.googleImported = entry.getAsBoolean("googleImported") != null && entry.getAsBoolean("googleImported");

        // Data
        String syncDateStr = entry.getAsString("syncDate");
        event.syncDate = syncDateStr != null ? SyncDate.fromString(syncDateStr) : null;


        // Enums (assume que o valor no banco está igual ao nome do enum)
        String colorStr = entry.getAsString("color");
        if (colorStr != null && !colorStr.isEmpty()) {
            try {
                event.color = Color.valueOf(colorStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                event.color = Color.GRAY; // padrão em caso de erro
            }
        }

        String contactStr = entry.getAsString("contactType");
        if (contactStr != null && !contactStr.isEmpty()) {
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
        String contactTypeStr = null;
        if (this.contactType != null) {
            contactTypeStr = this.contactType.toString();
        }
        values.put("contactType", contactTypeStr);
        values.put("googleImported", this.googleImported);
        values.put("googleId", this.googleId);

        return values;
    }

    public static EventModel getMock() {
        return new EventModel(
            1, "Evento 1", "Descrição", new SyncDate(07, 06, 2025), "09:00",
            "10:00",false, Color.BLUE, "Prova", true,
            false
        );
    }

    public void updateFromEditedEvent(
        EventModel editedEvent
    ) {
        setTitle(editedEvent.getTitle());
        setDescription(editedEvent.getDescription());
        setDate(editedEvent.getDate());
        setStartHour(editedEvent.getStartHour());
        setEndHour(editedEvent.getEndHour());
        setAllDay(editedEvent.isAllDay());
        setCategory(editedEvent.getCategory());
        setColor(editedEvent.getColor());
    }

    public static void validateEventModel(EventModel event) {
        if (event.getTitle().isEmpty()) {
            throw new ValidationException("title", "O título não pode ser vazio!");
        }
        if (event.getDescription().isEmpty()) {
            throw new ValidationException("description", "A descrição não pode ser vazia!");
        }
        if (event.getDate() == null) {
            throw new ValidationException("date", "A data não pode ser vazia!");
        }
        if (event.getStartHour().isEmpty()) {
            throw new ValidationException("startHour", "A hora de início não pode ser vazia!");
        }
        if (!ValidationHelper.validateHour(event.getStartHour())) {
            throw new ValidationException("startHour", "Informe uma hora de início válida!");
        }
        if (event.getEndHour().isEmpty()) {
            throw new ValidationException("endHour", "A hora de fim não pode ser vazia!");
        }
        if (!isStartHourBeforeOrEqualToEndHour(event.getStartHour(), event.getEndHour())) {
            throw new ValidationException("endHour", "A hora de fim não pode ser anterior à hora de início!");
        }
        if (!ValidationHelper.validateHour(event.getEndHour())) {
            throw new ValidationException("endHour", "Informe uma hora de fim válida!");
        }
        if (event.getCategory() != null && !checkValidValueForCategory(event.getCategory())) {
            throw new ValidationException("category", "Informe uma categoria válida!");
        }
        if (!event.isGoogleImported() && !event.isRubeusImported()) {
            throw new ValidationException("imported", "O evento deve ser criado em pelo menos um dos repositórios!");
        }
    }

    private static boolean checkValidValueForCategory(String category) {
        return (
                category.equals(EventModel.CATEGORY.TEST)||
                        category.equals(EventModel.CATEGORY.REUNION) ||
                        category.equals(EventModel.CATEGORY.LEISURE) ||
                        category.equals(EventModel.CATEGORY.WORK)
        );
    }

    private static boolean isStartHourBeforeOrEqualToEndHour(String start, String end) {
        String[] startParts = start.split(":");
        String[] endParts = end.split(":");

        int startHour = Integer.parseInt(startParts[0]);
        int startMinute = Integer.parseInt(startParts[1]);
        int endHour = Integer.parseInt(endParts[0]);
        int endMinute = Integer.parseInt(endParts[1]);

        if (startHour < endHour) {
            return true;
        } else if (startHour == endHour) {
            return startMinute <= endMinute;
        } else {
            return false;
        }
    }
}
