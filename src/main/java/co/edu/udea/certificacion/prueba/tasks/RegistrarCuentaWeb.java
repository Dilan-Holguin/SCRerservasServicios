package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.EscribirClave;
import co.edu.udea.certificacion.prueba.models.CuentaPrueba;
import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;

public final class RegistrarCuentaWeb {
    private RegistrarCuentaWeb() { }
    public static Performable con(CuentaPrueba cuenta) {
        return Task.where("{0} registra una cuenta " + cuenta.rol + " desde el formulario", actor -> {
            actor.attemptsTo(CerrarSesionWeb.siExiste(), Open.url(ConfiguracionUi.webUrl() + "/registro"),
                    WaitUntil.the(ReservaYa.NOMBRE, isVisible()).forNoMoreThan(15).seconds(),
                    Click.on(ReservaYa.rol(cuenta.rol)), Enter.theValue(cuenta.nombre).into(ReservaYa.NOMBRE),
                    Enter.theValue(cuenta.email).into(ReservaYa.EMAIL), EscribirClave.sinExponerla(cuenta.clave));
            if (cuenta.rol.equals("PROVEEDOR")) actor.attemptsTo(
                    Enter.theValue(cuenta.servicio).into(ReservaYa.NOMBRE_SERVICIO),
                    WaitUntil.the(ReservaYa.PRIMERA_CATEGORIA, isPresent()).forNoMoreThan(15).seconds(),
                    SelectFromOptions.byIndex(1).from(ReservaYa.CATEGORIA));
            actor.attemptsTo(Click.on(ReservaYa.CREAR_CUENTA),
                    WaitUntil.the(ReservaYa.CUENTA_CREADA, isVisible()).forNoMoreThan(15).seconds());
            cuenta.registrada = true;
        });
    }
}
