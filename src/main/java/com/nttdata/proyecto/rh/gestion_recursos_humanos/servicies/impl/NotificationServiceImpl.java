package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
    public void printNotification(String addressee, String subject, String message) {
        Logger logger = Logger.getLogger(getClass().getName());

        logger.info("====================================");
        logger.info("Notificación:");
        logger.log(Level.INFO, "Para: {}", addressee);
        logger.log(Level.INFO, "Asunto: {}", subject);
        logger.log(Level.INFO, "Mensaje: {}", message);
        logger.info("====================================");
    }

    public void sendNotification(String addressee, String subject, String message) {
        printNotification(addressee, subject, message);
    }
}
