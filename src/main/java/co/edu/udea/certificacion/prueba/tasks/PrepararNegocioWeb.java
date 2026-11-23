package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.models.*;
import co.edu.udea.certificacion.prueba.questions.DatosEnPantalla;
import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public final class PrepararNegocioWeb {
    private PrepararNegocioWeb() { }
    public static Performable para(DatosEscenario datos, int dias, boolean agenda, boolean cliente) {
        return Task.where("{0} prepara un negocio independiente mediante los formularios", actor -> {
            datos.fechaDentroDe(dias);
            datos.proveedor = new CuentaPrueba("PROVEEDOR");
            actor.attemptsTo(RegistrarCuentaWeb.con(datos.proveedor), VerificarCuentaWeb.de(datos.proveedor),
                    IniciarSesionWeb.con(datos.proveedor.email, datos.proveedor.clave),
                    WaitUntil.the(ReservaYa.panel("PROVEEDOR"), isVisible()).forNoMoreThan(15).seconds(),
                    WaitUntil.the(ReservaYa.fichaServicio(datos.proveedor.servicio), isVisible()).forNoMoreThan(15).seconds());
            datos.servicio = actor.asksFor(DatosEnPantalla.idServicio(datos.proveedor.servicio));
            if (agenda) actor.attemptsTo(CrearAgendaWeb.para(datos.servicio, datos.fecha.getDayOfWeek().getValue(), "08:00", "12:00"),
                    WaitUntil.the(ReservaYa.BLOQUE_CREADO, isVisible()).forNoMoreThan(15).seconds());
            datos.cuenta = datos.proveedor;
            datos.rolActual = "PROVEEDOR";
            if (cliente) {
                datos.cliente = new CuentaPrueba("CLIENTE");
                actor.attemptsTo(RegistrarCuentaWeb.con(datos.cliente), VerificarCuentaWeb.de(datos.cliente),
                        IniciarSesionWeb.con(datos.cliente.email, datos.cliente.clave),
                        WaitUntil.the(ReservaYa.panel("CLIENTE"), isVisible()).forNoMoreThan(15).seconds());
                datos.cuenta = datos.cliente;
                datos.rolActual = "CLIENTE";
            }
        });
    }
}
