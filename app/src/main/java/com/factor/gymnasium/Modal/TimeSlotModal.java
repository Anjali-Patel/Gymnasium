package com.factor.gymnasium.Modal;

public class TimeSlotModal {
    String time_slot;
    int booked_icon;

    public int getBooked_icon() {
        return booked_icon;
    }

    public void setBooked_icon(int booked_icon) {
        this.booked_icon = booked_icon;
    }

    public int getAvailable_icon() {
        return available_icon;
    }

    public void setAvailable_icon(int available_icon) {
        this.available_icon = available_icon;
    }

    int available_icon;


    public String getTime_slot() {
        return time_slot;
    }

    public void setTime_slot(String time_slot) {
        this.time_slot = time_slot;
    }


}
