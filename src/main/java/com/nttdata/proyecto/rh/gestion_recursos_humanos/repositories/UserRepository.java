package com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    User findByIdOrUsername(Long id, String username);

}
