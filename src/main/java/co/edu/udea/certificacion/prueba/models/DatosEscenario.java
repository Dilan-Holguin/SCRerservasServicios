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
}
