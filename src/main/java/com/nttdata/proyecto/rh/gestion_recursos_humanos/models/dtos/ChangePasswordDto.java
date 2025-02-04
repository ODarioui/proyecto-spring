package com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos;

public class ChangePasswordDto {

    private String curretnPassword;

    private String newPassword;

    public ChangePasswordDto(String curretnPassword, String newPassword) {
        this.curretnPassword = curretnPassword;
        this.newPassword = newPassword;
    }

    public ChangePasswordDto() {
    }

    public String getCurretnPassword() {
        return this.curretnPassword;
    }

    public void setCurretnPassword(String curretnPassword) {
        this.curretnPassword = curretnPassword;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
