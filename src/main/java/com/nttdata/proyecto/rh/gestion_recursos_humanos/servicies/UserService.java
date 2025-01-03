package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.User;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ChangePasswordDto;

public interface UserService {

    public User registerUser(User user);

    public User changePassword(String username, ChangePasswordDto changePasswordDto);
}