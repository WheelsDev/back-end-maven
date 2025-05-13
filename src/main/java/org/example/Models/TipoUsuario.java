package org.example.Models;

public enum TipoUsuario {
    ADMIN, RECEPCIONISTA;

    public static TipoUsuario fromString(String tipo) {
        return TipoUsuario.valueOf(tipo.trim().toUpperCase());
    }
}
