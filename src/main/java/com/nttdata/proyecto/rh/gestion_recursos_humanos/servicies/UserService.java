package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies;

import java.util.List;
import java.util.Map;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.User;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ChangePasswordDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ChangeRoleDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.DeleteUserDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserRegisterDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserStatusDto;

public interface UserService {

    public User registerUser(UserRegisterDto userR);

    public User changePassword(ChangePasswordDto changePasswordDto);

    public UserDto changeRole(ChangeRoleDto changeRoleDto);

    public Map<String, Object> deleteUsers(List<DeleteUserDto> usersDelete);

    public void selfDelete();

    public UserDto updateAnyUser(User user);

    public UserDto updateEmployee(User user);

    public UserDto statusUser(UserStatusDto userStatusDto);

    public UserDto statusEmployee(UserStatusDto userStatusDto);

    public Map<String, Object> allUsers();

    public Map<String, Object> allEmployees();

    public UserDto userInfo();
}