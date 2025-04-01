package com.ugb.miprimeraaplicacion;

import java.util.Base64;

public class utilidades {
    static String url_consulta ="http://192.168.80.39:5984/agenda/_design/agenda/_view/agenda";
    static String url_mto="http://192.168.80.39:5984/agenda";
    static String user="Josuealejandro";
    static String password="123456789";
    static String credencialesCodificadas = Base64.getEncoder().encodeToString((user + ":"+password).getBytes());

    public String generarUnicoid() {
        return java.util.UUID.randomUUID().toString();
    }

}

