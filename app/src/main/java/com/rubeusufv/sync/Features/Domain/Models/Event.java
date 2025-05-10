package com.rubeusufv.sync.Features.Domain.Models;

import com.rubeusufv.sync.Features.Domain.Types.Color;

import java.util.Date;

public class Event {

    private String title;
    private String description;
    private Date date;
    private String startHour;
    private String endHour;
    private boolean allDay;
    private Color color;
    private String category;
    private boolean rubeusSynchronized;
    private boolean googleSynchronized;

    public Event(
        String title, String description, Date date, String startHour, String endHour,
        boolean allDay, Color color, String category, boolean rubeusSynchronized,
        boolean googleSynchronized
    ) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.endHour = endHour;
        this.startHour = startHour;
        this.allDay = allDay;
        this.color = color;
        this.category = category;
        this.rubeusSynchronized = rubeusSynchronized;
        this.googleSynchronized = googleSynchronized;
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

    public boolean isRubeusSynchronized() {
        return rubeusSynchronized;
    }

    public void setRubeusSynchronized(boolean rubeusSynchronized) {
        this.rubeusSynchronized = rubeusSynchronized;
    }

    public boolean isGoogleSynchronized() {
        return googleSynchronized;
    }

    public void setGoogleSynchronized(boolean googleSynchronized) {
        this.googleSynchronized = googleSynchronized;
    }
}
