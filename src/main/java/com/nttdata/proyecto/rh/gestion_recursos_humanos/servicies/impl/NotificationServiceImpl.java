package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.impl;

import org.springframework.stereotype.Service;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
    public void printNotification(String addressee, String subject, String message){
        System.out.println("====================================");
        System.out.println("Notificación:");
        System.out.println("Para: " + addressee);
        System.out.println("Asunto: " + subject);
        System.out.println("Mensaje: " + message);
        System.out.println("====================================");
    }

    public void sendNotification(String addressee, String subject, String message){
        printNotification(addressee, subject, message); 
    }
}
