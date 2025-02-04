package com.nttdata.proyecto.rh.gestion_recursos_humanos.util;

import java.util.List;

public interface Security {

    public String getUsernameLogged();

    public List<String> userAuthority();
}
