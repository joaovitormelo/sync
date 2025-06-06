package com.rubeusufv.sync.Features.Domain.Models;

import com.rubeusufv.sync.Features.Domain.Types.Color;
import com.rubeusufv.sync.Features.Domain.Types.ContactType;

import java.util.Date;

public class EventModel {
    private int id;
    private int userId;
    private String title;
    private String description;
    private Date date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public EventModel(
            int id, int userId, String title, String description, Date date, String startHour,
            String endHour, boolean allDay, Color color, String category,
            boolean rubeusImported, int rubeusId, ContactType contactType,
            boolean googleImported, int googleId
    ) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.date = date;
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
        int userId, String title, String description, Date date, String startHour,
        String endHour, boolean allDay, Color color, String category,
        boolean rubeusImported, boolean googleImported
    ) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.endHour = endHour;
        this.startHour = startHour;
        this.allDay = allDay;
        this.color = color;
        this.category = category;
        this.rubeusImported = rubeusImported;
        this.googleImported = googleImported;
    }

    public static EventModel getMock() {
        return new EventModel(
            1, "Evento 1", "Descrição", new Date(), "09:00",
            "10:00",false, Color.BLUE, "A", true,
            false
        );
    }
}
