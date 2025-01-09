package com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos;

import java.util.Date;

public class ResponseDto {

    private String message;
    private Date date;
    private Integer status;
    private Object object;

    public ResponseDto() {
    }

    public ResponseDto(String message, Date date, Integer status, Object object) {
        this.message = message;
        this.date = date;
        this.status = status;
        this.object = object;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getObject() {
        return this.object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
