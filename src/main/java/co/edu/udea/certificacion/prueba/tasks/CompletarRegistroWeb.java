package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.EscribirClave;
import co.edu.udea.certificacion.prueba.models.CuentaPrueba;
import co.edu.udea.certificacion.prueba.userinterfaces.*;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;

public final class CompletarRegistroWeb {
    private CompletarRegistroWeb() { }
    public static Performable con(CuentaPrueba cuenta, String defecto) {
        return Task.where("{0} completa el formulario de registro: " + defecto, actor -> {
            actor.attemptsTo(CerrarSesionWeb.siExiste(), Open.url(ConfiguracionUi.webUrl() + "/registro"),
                    WaitUntil.the(ReservaYa.NOMBRE, isVisible()).forNoMoreThan(15).seconds(),
                    Click.on(ReservaYa.rol(cuenta.rol)),
                    Enter.theValue(defecto.equals("nombre vacío") ? "" : cuenta.nombre).into(ReservaYa.NOMBRE),
                    Enter.theValue(defecto.equals("correo vacío") ? "" : defecto.equals("correo inválido") ? "correo-invalido" : cuenta.email).into(ReservaYa.EMAIL),
                    EscribirClave.sinExponerla(defecto.equals("clave corta") ? "12345678901" : cuenta.clave));
            if (cuenta.rol.equals("PROVEEDOR")) {
                if (!defecto.equals("sin servicio"))
                    actor.attemptsTo(Enter.theValue(cuenta.servicio).into(ReservaYa.NOMBRE_SERVICIO));
                if (!defecto.equals("sin servicio") && !defecto.equals("sin categoría"))
                    actor.attemptsTo(WaitUntil.the(ReservaYa.PRIMERA_CATEGORIA, isPresent()).forNoMoreThan(15).seconds(),
                            SelectFromOptions.byIndex(1).from(ReservaYa.CATEGORIA));
                if (defecto.equals("duración inválida"))
                    actor.attemptsTo(Enter.theValue("0").into(FlujosUi.campoRegistro("servicios[0].duracionMinutos")));
                if (defecto.equals("capacidad inválida"))
                    actor.attemptsTo(Enter.theValue("0").into(FlujosUi.campoRegistro("servicios[0].capacidad")));
            }
        });
    }
}
