package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies;

public interface NotificationService {
    public void printNotification(String addressee, String subject, String message);

    public void sendNotification(String addressee, String subject, String message);
}
