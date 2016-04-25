package com.app.dto;

import java.io.Serializable;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
public class StatusDto implements Serializable {
    private String status;
    private String message;
    private Object data;

    public StatusDto(){super();}
    public StatusDto(String status){
        this.status = status;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
