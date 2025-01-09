package com.nttdata.proyecto.rh.gestion_recursos_humanos.exceptions;

import java.util.Date;

public class ErrorMessage {

    private String title;
    private String message;
    private Integer status;
    private Date date;

    public ErrorMessage() {
    }

    public ErrorMessage(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
