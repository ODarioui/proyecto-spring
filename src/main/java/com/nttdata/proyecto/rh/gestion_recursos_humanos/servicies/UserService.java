package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.User;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ChangePasswordDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ChangeRoleDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserDto;

public interface UserService {

    public User registerUser(User user);

    public User changePassword(String username, ChangePasswordDto changePasswordDto);

    public UserDto changeRole(ChangeRoleDto changeRoleDto);
}