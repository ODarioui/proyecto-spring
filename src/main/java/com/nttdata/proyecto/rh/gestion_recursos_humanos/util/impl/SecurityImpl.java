package com.nttdata.proyecto.rh.gestion_recursos_humanos.util.impl;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.util.Security;

@Component
public class SecurityImpl implements Security {

    @Override
    public String getUsernameLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @Override
    public List<String> userAuthority() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

}
