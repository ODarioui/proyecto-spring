package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.exceptions.CustomException;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.exceptions.InsufficientPermissionsException;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.User;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ChangePasswordDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ChangeRoleDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.DeleteUserDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserRegisterDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserStatusDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.enums.Role;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.UserRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.impl.UserServiceImpl;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.util.Security;

@SpringBootTest
class UserServiceTest {

    private final UserRegisterDto userRegisterDto = new UserRegisterDto();
    private final User user = new User();
    private final User userUpdate = new User();

    private final Long id = 1L;
    private final String name = "user";
    private final String lastname1 = "user";
    private final String lastname2 = "user";
    private final String email = "user@email.com";
    private final String username = "user";
    private final String password = "1234";
    private final String phone = "123456789";
    private final String address = "";
    private final Date creationDate = new Date();
    private final String status = "";

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private Security security;

    @Mock
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        userServiceImpl = new UserServiceImpl(userRepository, passwordEncoder, security);

        userRegisterDto.setName(name);
        userRegisterDto.setLastname1(lastname1);
        userRegisterDto.setLastname2(lastname2);
        userRegisterDto.setEmail(email);
        userRegisterDto.setUsername(username);
        userRegisterDto.setPassword(password);
        userRegisterDto.setPhone(phone);
        userRegisterDto.setAddress(address);
        userRegisterDto.setCreationDate(creationDate);
        userRegisterDto.setStatus(status);

        String encodedPassword = passwordEncoder.encode(password);
        user.setId(id);
        user.setEmail(userRegisterDto.getEmail());
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(encodedPassword);
        user.setRole(Role.USER);

        userUpdate.setId(1L);
        userUpdate.setUsername("oldUsername");
        userUpdate.setPassword("oldPassword");
        userUpdate.setRole(Role.USER);
    }

    @Test
    void registerUserTest() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);

        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userServiceImpl.registerUser(userRegisterDto);

        assertNotNull(registeredUser);
        assertEquals("user", registeredUser.getUsername());
        assertEquals("user@email.com", registeredUser.getEmail());
        assertTrue(passwordEncoder.matches(password, user.getPassword()));
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        CustomException e = Assert.assertThrows(CustomException.class,
                () -> userServiceImpl.registerUser(userRegisterDto));
        assertEquals("Ya existe un usuario registrado con este email", e.getMessage());
    }

    @Test
    void testRegisterUser_UsernameAlreadyExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        CustomException e = Assert.assertThrows(CustomException.class,
                () -> userServiceImpl.registerUser(userRegisterDto));
        assertEquals("Ya existe un usuario con este nombre de usuario", e.getMessage());
    }

    @Test
    void testRegisterUser_EmailIsNull() {
        userRegisterDto.setEmail(null);

        CustomException e = Assert.assertThrows(CustomException.class,
                () -> userServiceImpl.registerUser(userRegisterDto));
        assertEquals("Email obligatorio", e.getMessage());
    }

    @Test
    void testRegisterUser_UsernameIsNull() {
        userRegisterDto.setUsername(null);

        CustomException e = Assert.assertThrows(CustomException.class,
                () -> userServiceImpl.registerUser(userRegisterDto));
        assertEquals("Username obligatorio", e.getMessage());
    }

    @Test
    void changePasswordTest() {

        ChangePasswordDto changePasswordDto = new ChangePasswordDto("1234", "newPassword");

        when(security.getUsernameLogged()).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User result = userServiceImpl.changePassword(changePasswordDto);

        assertTrue(passwordEncoder.matches(changePasswordDto.getNewPassword(), result.getPassword()));
        assertNotNull(result);

    }

    @Test
    void changePasswordUserNotFoundTest() {

        ChangePasswordDto changePasswordDto = new ChangePasswordDto("1234", "newPassword");

        when(security.getUsernameLogged()).thenReturn(username);
        when(userRepository.findByUsername(username)).thenThrow();

        Exception e = Assert.assertThrows(Exception.class,
                () -> userServiceImpl.changePassword(changePasswordDto));
        assertNotNull(e);
    }

    @Test
    void changePasswordNotMatchesTest() {

        ChangePasswordDto changePasswordDto = new ChangePasswordDto("123456", "newPassword");

        when(security.getUsernameLogged()).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User result = userServiceImpl.changePassword(changePasswordDto);

        assertNull(result);
    }

    @Test
    void cahngeRoleUserNotFound() {

        ChangeRoleDto changeRoleDto = new ChangeRoleDto(1L, "testUser", Role.ADMIN);
        List<String> roles = List.of("ROLE_USER");
        when(security.userAuthority()).thenReturn(roles);
        when(userRepository.findByIdOrUsername(changeRoleDto.getId(), changeRoleDto.getUsername()))
                .thenReturn(null);

        Exception e = assertThrows(Exception.class, () -> userServiceImpl.changeRole(changeRoleDto));
        assertEquals("NO se encontro ningun usuario", e.getMessage());
    }

    @Test
    void changeRoleNullTest() {

        ChangeRoleDto changeRoleDto = new ChangeRoleDto(1L, "user", null);
        List<String> roles = List.of("ROLE_ADMIN");
        when(security.userAuthority()).thenReturn(roles);
        when(userRepository.findByIdOrUsername(changeRoleDto.getId(), changeRoleDto.getUsername()))
                .thenReturn(user);

        Exception e = assertThrows(IllegalArgumentException.class, () -> userServiceImpl.changeRole(changeRoleDto));
        assertEquals("No se ha indicado ningun rol", e.getMessage());
    }

    @Test
    void changeRoleUserNotPermitedTest() {

        ChangeRoleDto changeRoleDto = new ChangeRoleDto(1L, "testUser", Role.ADMIN);

        List<String> roles = List.of("ROLE_USER");
        when(security.userAuthority()).thenReturn(roles);

        when(userRepository.findByIdOrUsername(changeRoleDto.getId(), changeRoleDto.getUsername()))
                .thenReturn(user);

        assertEquals(Role.USER, user.getRole());
        Exception e = assertThrows(InsufficientPermissionsException.class,
                () -> userServiceImpl.changeRole(changeRoleDto));
        assertEquals("No tienes permisos suficentes para cometer esta accion", e.getMessage());
    }

    @Test
    void changeRoleEmployeeNotPermitedTest() {

        ChangeRoleDto changeRoleDto = new ChangeRoleDto(1L, "testUser", Role.USER);

        List<String> roles = List.of("ROLE_EMPLOYEE");
        when(security.userAuthority()).thenReturn(roles);

        when(userRepository.findByIdOrUsername(changeRoleDto.getId(), changeRoleDto.getUsername()))
                .thenReturn(user);

        Exception e = assertThrows(InsufficientPermissionsException.class,
                () -> userServiceImpl.changeRole(changeRoleDto));
        assertEquals("No tienes permisos suficentes para cometer esta accion", e.getMessage());
    }

    @Test
    void changeRoleHRTest() {
        ChangeRoleDto changeRoleDto = new ChangeRoleDto(1L, "user", Role.HR); //

        when(userRepository.findByIdOrUsername(changeRoleDto.getId(),
                changeRoleDto.getUsername()))
                .thenReturn(user);

        List<String> roles = List.of("ROLE_ADMIN");

        when(security.userAuthority()).thenReturn(roles);
        when(userRepository.save(user)).thenReturn(user);

        UserDto result = userServiceImpl.changeRole(changeRoleDto);

        assertNotNull(result);
        assertEquals(Role.HR, result.getRole());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void changeRoleHrErrorTest() {

        ChangeRoleDto changeRoleDto = new ChangeRoleDto(1L, "testUser",
                Role.HR);
        when(userRepository.findByIdOrUsername(changeRoleDto.getId(),
                changeRoleDto.getUsername()))
                .thenReturn(user);

        List<String> roles = List.of("ROLE_HR");

        when(security.userAuthority()).thenReturn(roles);
        when(userRepository.save(user)).thenReturn(user);

        UserDto result = userServiceImpl.changeRole(changeRoleDto);

        assertEquals(Role.USER, result.getRole());
        assertNotNull(result);
    }

    @Test
    void changeRoleHrTest() {

        ChangeRoleDto changeRoleDto = new ChangeRoleDto(1L, "testUser",
                Role.EMPLOYEE);
        when(userRepository.findByIdOrUsername(changeRoleDto.getId(),
                changeRoleDto.getUsername()))
                .thenReturn(user);

        List<String> roles = List.of("ROLE_HR");

        when(security.userAuthority()).thenReturn(roles);
        when(userRepository.save(user)).thenReturn(user);

        UserDto result = userServiceImpl.changeRole(changeRoleDto);

        assertEquals(Role.EMPLOYEE, result.getRole());
        assertNotNull(result);
    }

    @Test
    void changeRoleAdminErrorTest() {

        ChangeRoleDto changeRoleDto = new ChangeRoleDto(1L, "testUser",
                Role.ADMIN);
        when(userRepository.findByIdOrUsername(changeRoleDto.getId(),
                changeRoleDto.getUsername()))
                .thenReturn(user);

        List<String> roles = List.of("ROLE_ADMIN");

        when(security.userAuthority()).thenReturn(roles);
        when(userRepository.save(user)).thenReturn(user);

        UserDto result = userServiceImpl.changeRole(changeRoleDto);

        assertEquals(Role.USER, result.getRole());
        assertNotNull(result);
    }

    @SuppressWarnings("unchecked")
    @Test
    void deleteUsersTest() {

        DeleteUserDto validUserDelete = new DeleteUserDto(1L, "user");
        when(userRepository.findByIdOrUsername(validUserDelete.getId(), validUserDelete.getUsername()))
                .thenReturn(user);

        List<DeleteUserDto> usersToDelete = Collections.singletonList(validUserDelete);
        Map<String, Object> result = userServiceImpl.deleteUsers(usersToDelete);

        assertNotNull(result);
        assertTrue(result.size() > 0);
        assertEquals("Usuario eliminado", ((Map<String, Object>) result.get("1")).get("message"));
    }

    @SuppressWarnings("unchecked")
    @Test
    void deleteUsersNotFoundTest() {
        DeleteUserDto invalidUserDelete = new DeleteUserDto(11L, "user11");

        when(userRepository.findByIdOrUsername(invalidUserDelete.getId(), invalidUserDelete.getUsername()))
                .thenThrow();

        List<DeleteUserDto> usersToDelete = Collections.singletonList(invalidUserDelete);
        Map<String, Object> result = userServiceImpl.deleteUsers(usersToDelete);

        assertEquals("No se pudo eliminar este usuario", ((Map<String, Object>) result.get("1")).get("message"));
        assertNotNull(((Map<String, Object>) result.get("1")).get("Error"));
    }

    @Test
    void selfDeleteTest() {
        when(security.getUsernameLogged()).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        userServiceImpl.selfDelete();
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void selfDeleteNullTest() {
        when(security.getUsernameLogged()).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(null);
        CustomException e = Assert.assertThrows(CustomException.class, () -> userServiceImpl.selfDelete());
        assertNotNull(e);
        assertEquals("No se pudo eliminar", e.getMessage());
    }

    @Test
    void updateAnyUserTest() {

        when(userRepository.findByIdOrUsername(user.getId(), user.getUsername())).thenReturn(userUpdate);
        when(userRepository.save(user)).thenReturn(user);

        UserDto result = userServiceImpl.updateAnyUser(user);

        assertNotNull(result);
        verify(userRepository).save(user);
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void updateAnyUserInsuficientPermisonsTest() {

        userUpdate.setRole(Role.ADMIN);
        when(userRepository.findByIdOrUsername(user.getId(), user.getUsername())).thenReturn(userUpdate);

        InsufficientPermissionsException exception = assertThrows(
                InsufficientPermissionsException.class,
                () -> userServiceImpl.updateAnyUser(user));

        assertEquals("No tiene permisios sobre este usuario ", exception.getMessage());
        verify(userRepository, never()).save(user);
    }

    @Test
    void updateAnyUserNullRoleTest() {
        userUpdate.setRole(null); // Set role to null

        when(userRepository.findByIdOrUsername(user.getId(), user.getUsername())).thenReturn(userUpdate);

        InsufficientPermissionsException exception = assertThrows(
                InsufficientPermissionsException.class,
                () -> userServiceImpl.updateAnyUser(user));

        assertEquals("No tiene permisios sobre este usuario ", exception.getMessage());
        verify(userRepository, never()).save(user);
    }

    @Test
    void updateEmployeeTest() {

        when(userRepository.findByIdOrUsername(user.getId(), user.getUsername())).thenReturn(userUpdate);
        when(userRepository.save(user)).thenReturn(user);

        UserDto result = userServiceImpl.updateEmployee(user);

        assertNotNull(result);
        verify(userRepository).save(user);
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void updateEmployeeInsuficientPermisonsTest() {

        userUpdate.setRole(Role.ADMIN);
        when(userRepository.findByIdOrUsername(user.getId(), user.getUsername())).thenReturn(userUpdate);

        InsufficientPermissionsException exception = assertThrows(
                InsufficientPermissionsException.class,
                () -> userServiceImpl.updateEmployee(user));

        assertEquals("No tiene permisios sobre  este usuario", exception.getMessage());
        verify(userRepository, never()).save(user);
    }

    @Test
    void updateEmployeeNullRoleTest() {
        userUpdate.setRole(null); // Set role to null

        when(userRepository.findByIdOrUsername(user.getId(), user.getUsername())).thenReturn(userUpdate);

        InsufficientPermissionsException exception = assertThrows(
                InsufficientPermissionsException.class,
                () -> userServiceImpl.updateEmployee(user));

        assertEquals("No tiene permisios sobre  este usuario", exception.getMessage());
        verify(userRepository, never()).save(user);
    }

    @Test
    void statusUserTest() {

        UserStatusDto userStatusDto = new UserStatusDto("ACTIVE", 1L, "user");
        when(userRepository.findByIdOrUsername(userStatusDto.getId(), userStatusDto.getUsername()))
                .thenReturn(userUpdate);

        UserDto result = userServiceImpl.statusUser(userStatusDto);

        assertNotNull(result);
        assertEquals("ACTIVE", result.getStatus());
        verify(userRepository).save(userUpdate);
    }

    @Test
    void statusUserInsufficientPermisonsTest() {

        userUpdate.setRole(Role.ADMIN);
        UserStatusDto userStatusDto = new UserStatusDto("ACTIVE", 1L, "user");
        when(userRepository.findByIdOrUsername(userStatusDto.getId(), userStatusDto.getUsername()))
                .thenReturn(userUpdate);

        InsufficientPermissionsException exception = assertThrows(
                InsufficientPermissionsException.class,
                () -> userServiceImpl.statusUser(userStatusDto));

        assertEquals("No tiene permisios sobre este usuario", exception.getMessage());
        verify(userRepository, never()).save(userUpdate); // No debe llamarse save si hay excepción
    }

    @Test
    void statusUserNullRoleTest() {
        // Role is null (should be treated as insufficient permissions)
        userUpdate.setRole(null);
        UserStatusDto userStatusDto = new UserStatusDto("ACTIVE", 1L, "user");

        when(userRepository.findByIdOrUsername(userStatusDto.getId(), userStatusDto.getUsername()))
                .thenReturn(userUpdate);

        InsufficientPermissionsException exception = assertThrows(
                InsufficientPermissionsException.class,
                () -> userServiceImpl.statusUser(userStatusDto));

        assertEquals("No tiene permisios sobre este usuario", exception.getMessage());
        verify(userRepository, never()).save(userUpdate); // No debe llamarse save si hay excepción
    }

    @Test
    void statusEmployeeTest() {

        UserStatusDto userStatusDto = new UserStatusDto("ACTIVE", 1L, "user");
        when(userRepository.findByIdOrUsername(userStatusDto.getId(), userStatusDto.getUsername()))
                .thenReturn(userUpdate);

        UserDto result = userServiceImpl.statusEmployee(userStatusDto);

        assertNotNull(result);
        assertEquals("ACTIVE", result.getStatus());
        verify(userRepository).save(userUpdate);
    }

    @Test
    void statusEmployeeInsufficientPermisonsTest() {

        userUpdate.setRole(Role.ADMIN);
        UserStatusDto userStatusDto = new UserStatusDto("ACTIVE", 1L, "user");
        when(userRepository.findByIdOrUsername(userStatusDto.getId(), userStatusDto.getUsername()))
                .thenReturn(userUpdate);

        InsufficientPermissionsException exception = assertThrows(
                InsufficientPermissionsException.class,
                () -> userServiceImpl.statusEmployee(userStatusDto));

        assertEquals("No tiene permisios sobre este usuario", exception.getMessage());
        verify(userRepository, never()).save(userUpdate); // No debe llamarse save si hay excepción
    }

    @Test
    void statusEmployeeNullRoleTest() {
        // Role is null (should be treated as insufficient permissions)
        userUpdate.setRole(null);
        UserStatusDto userStatusDto = new UserStatusDto("ACTIVE", 1L, "user");

        when(userRepository.findByIdOrUsername(userStatusDto.getId(), userStatusDto.getUsername()))
                .thenReturn(userUpdate);

        InsufficientPermissionsException exception = assertThrows(
                InsufficientPermissionsException.class,
                () -> userServiceImpl.statusEmployee(userStatusDto));

        assertEquals("No tiene permisios sobre este usuario", exception.getMessage());
        verify(userRepository, never()).save(userUpdate); // No debe llamarse save si hay excepción
    }

    @Test
    void allUsersEmptyListTest() {

        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        Map<String, Object> result = userServiceImpl.allUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty(), "The result map should be empty when there are no users.");
        verify(userRepository).findAll();
    }

    @Test
    void allUsersTest() {
        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setPassword("1234");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user, user2));

        Map<String, Object> result = userServiceImpl.allUsers();

        assertNotNull(result);
        assertEquals(2, result.size(), "The result map should contain two users.");

        assertTrue(result.containsKey("1"));
        assertTrue(result.containsKey("2"));

        assertEquals("user", ((UserDto) result.get("1")).getUsername());
        assertEquals("user2", ((UserDto) result.get("2")).getUsername());

        verify(userRepository).findAll();
    }

    @Test
    void allUsersErrorTest() {
        when(userRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userServiceImpl.allUsers());

        assertEquals("Database error", exception.getMessage());
        verify(userRepository).findAll();
    }

    @Test
    void allEmployeesEmptyListTest() {

        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        Map<String, Object> result = userServiceImpl.allEmployees();

        assertNotNull(result);
        assertTrue(result.isEmpty(), "The result map should be empty when there are no users.");
        verify(userRepository).findAll();
    }

    @Test
    void allEmployeesTest() {
        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setPassword("1234");

        User user3 = new User();
        user3.setId(3L);
        user3.setUsername("user3");
        user3.setPassword("1234");
        user3.setRole(Role.ADMIN);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user, user2, user3));

        Map<String, Object> result = userServiceImpl.allEmployees();

        assertNotNull(result);
        assertEquals(2, result.size(), "The result map should contain two users.");

        assertTrue(result.containsKey("1"));
        assertTrue(result.containsKey("2"));

        assertEquals("user", ((UserDto) result.get("1")).getUsername());
        assertEquals("user2", ((UserDto) result.get("2")).getUsername());

        verify(userRepository).findAll();
    }

    @Test
    void allEmployeesErrorTest() {
        when(userRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userServiceImpl.allEmployees());

        assertEquals("Database error", exception.getMessage());
        verify(userRepository).findAll();
    }

    @Test
    void userInfoTest() {

        when(security.getUsernameLogged()).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        UserDto userDto = userServiceImpl.userInfo();
        assertNotNull(userDto);
        assertEquals(user.getUsername(), userDto.getUsername());
    }

    @Test
    void userInfoErrorTest() {

        when(security.getUsernameLogged()).thenReturn("user");
        when(userRepository.findByUsername("user")).thenThrow();

        assertThrows(Exception.class, () -> userServiceImpl.userInfo());
    }
}
