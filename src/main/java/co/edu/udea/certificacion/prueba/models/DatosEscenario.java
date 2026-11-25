package co.edu.udea.certificacion.prueba.models;

import java.time.LocalDate;
import java.time.ZoneId;

/** Estado del flujo visible, sin conexiones ni creación de datos fuera de la aplicación. */
public final class DatosEscenario {
    public static final String CLAVE = "datosEscenarioUi";
    public final java.util.Map<Integer,String> serviciosExtra = new java.util.LinkedHashMap<>();
    public final java.util.List<Integer> reservasCreadas = new java.util.ArrayList<>();
    public int servicioSecundario;
    public CuentaPrueba otroCliente;
    public String nombreRecurso;
    public String mensajeIngreso;
    public String tokenVerificacion;
    public CuentaPrueba cuenta;
    public CuentaPrueba proveedor;
    public CuentaPrueba cliente;
    public int servicio;
    public int reserva;
    public String rolActual;
    public LocalDate fecha;

    public void fechaDentroDe(int dias) {
        fecha = LocalDate.now(ZoneId.of("America/Bogota")).plusDays(dias);
    }

    public CuentaPrueba nuevoNegocio(int dias) {
        fechaDentroDe(dias);
        proveedor = new CuentaPrueba("PROVEEDOR");
        return proveedor;
    }

    public CuentaPrueba nuevoCliente(boolean adicional) {
        CuentaPrueba nueva = new CuentaPrueba("CLIENTE");
        if (adicional) {
            otroCliente = nueva;
        } else {
            cliente = nueva;
        }
        return nueva;
    }

    /** Día de la semana (1 = lunes) de la fecha preparada. */
    public int diaDeLaAgenda() {
        return fecha.getDayOfWeek().getValue();
    }

    public String nombreServicioAdicional() {
        return proveedor.servicio + " B";
    }

    public String nuevoRecurso() {
        nombreRecurso = "Recurso " + java.util.UUID.randomUUID();
        return nombreRecurso;
    }

    /** Proveedor cuyos datos se pueden limpiar, o null si no hay ninguno verificado. */
    public CuentaPrueba proveedorPorLimpiar() {
        CuentaPrueba candidato = proveedor != null ? proveedor
                : cuenta != null && cuenta.rol.equals("PROVEEDOR") ? cuenta : null;
        return candidato != null && candidato.verificada ? candidato : null;
    }

    public java.util.Set<Integer> reservasPorLimpiar() {
        var reservas = new java.util.LinkedHashSet<>(reservasCreadas);
        if (reserva != 0) {
            reservas.add(reserva);
        }
        return reservas;
    }

    public java.util.Set<String> serviciosPorLimpiar(CuentaPrueba dueno) {
        var servicios = new java.util.LinkedHashSet<String>();
        servicios.add(dueno.servicio);
        servicios.addAll(serviciosExtra.values());
        return servicios;
    }
}
