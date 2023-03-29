package com.parking.parking.controller.Sms;

public class SmsResponse {
    private boolean success;
    private String message;

    public SmsResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
