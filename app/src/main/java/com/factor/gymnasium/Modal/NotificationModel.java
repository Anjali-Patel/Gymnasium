package com.factor.gymnasium.Modal;

public class NotificationModel {
    String message,date_time;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public int getGym_logo() {
        return gym_logo;
    }

    public void setGym_logo(int gym_logo) {
        this.gym_logo = gym_logo;
    }

    int gym_logo;
}
