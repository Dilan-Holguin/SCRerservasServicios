package co.edu.udea.certificacion.prueba.models;

import java.util.UUID;

/** Datos exclusivos de un escenario; nunca se comparten cuentas entre pruebas. */
public final class CuentaPrueba {
    public final String rol;
    public final String email;
    public final String nombre;
    public final String clave = "Reserva-E2E-segura-2026!";
    public final String servicio;
    public boolean registrada;
    public boolean verificada;

    public CuentaPrueba(String rol) {
        this.rol = rol;
        String id = UUID.randomUUID().toString();
        email = "ui-" + id + "@example.test";
        nombre = "Prueba UI " + id;
        servicio = "Servicio UI " + id;
    }
}
