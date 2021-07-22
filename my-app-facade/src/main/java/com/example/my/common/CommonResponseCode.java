package com.example.my.common;

public enum CommonResponseCode {

    RC_SUCCESS("1", "success"),
    RC_ERROR("0", "fail");

    private String responseCode;
    private String responseMessage;

    CommonResponseCode(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
