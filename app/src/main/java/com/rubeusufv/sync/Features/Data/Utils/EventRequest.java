package com.rubeusufv.sync.Features.Data.Utils;

public class EventRequest {
    private String title;
    private String description;
    private String date;
    private String startHour;
    private String endHour;
    private boolean allDay;
    private String color;
    private String category;

    public EventRequest(String title, String description, String date, String startHour, String endHour, boolean allDay, String color, String category) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
        this.allDay = allDay;
        this.color = color;
        this.category = category;
    }

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public String getStartHour() {return startHour;}

    public void setStartHour(String startHour) {this.startHour = startHour;}

    public String getEndHour() {return endHour;}

    public void setEndHour(String endHour) {this.endHour = endHour;}

    public boolean isAllDay() {return allDay;}

    public void setAllDay(boolean allDay) {this.allDay = allDay;}

    public String getColor() {return color;}

    public void setColor(String color) {this.color = color;}

    public String getCategory() {return category;}

    public void setCategory(String category) {this.category = category;}
}
