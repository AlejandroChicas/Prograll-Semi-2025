package com.example.proyectofinal;

import java.util.Base64;

public class utilidades {
    static String url_consulta = "http://192.168.1.5:5984/taskplanner/_design/taskplanner/_view/tareas-view";
    static String url_mto = "http://192.168.1.5:5984/taskplanner";
    static String user = "admin";
    static String passwd = "1234";

    static String credencialesCodificadas = Base64.getEncoder().encodeToString((user + ":" + passwd).getBytes());

    public String generarUnicoId() {
        return java.util.UUID.randomUUID().toString();
    }
}

