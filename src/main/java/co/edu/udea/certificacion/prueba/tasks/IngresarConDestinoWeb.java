package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.EscribirClave;
import co.edu.udea.certificacion.prueba.models.CuentaPrueba;
import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/** Conserva el destino y el turno que la navegación de la UI guardó antes del ingreso. */
public final class IngresarConDestinoWeb {
    private IngresarConDestinoWeb() { }
    public static Performable con(CuentaPrueba cuenta) {
        return Task.where("{0} ingresa sin perder el turno pendiente",
                WaitUntil.the(ReservaYa.EMAIL, isVisible()).forNoMoreThan(15).seconds(),
                Enter.theValue(cuenta.email).into(ReservaYa.EMAIL), EscribirClave.sinExponerla(cuenta.clave),
                Click.on(ReservaYa.INGRESAR));
    }
}
