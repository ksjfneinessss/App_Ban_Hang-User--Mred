package com.example.btlandroid.Model;

import java.util.List;

public class ThongKeModel {

    private boolean success;
    private String message;
    private List<ThongKe> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ThongKe> getResult() {
        return result;
    }

    public void setResult(List<ThongKe> result) {
        this.result = result;
    }
}
