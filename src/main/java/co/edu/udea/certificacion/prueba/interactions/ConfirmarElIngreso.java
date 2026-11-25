package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.models.CuentaPrueba;
import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

public class ConfirmarElIngreso implements Interaction {

    private final DatosEscenario datos;
    private final CuentaPrueba cuenta;

    public ConfirmarElIngreso(DatosEscenario datos, CuentaPrueba cuenta) {
        this.datos = datos;
        this.cuenta = cuenta;
    }

    @Override
    @Step("{0} espera su panel y recuerda la sesión")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Esperar.visible(ReservaYa.panel(cuenta.rol)));
        datos.cuenta = cuenta;
        datos.rolActual = cuenta.rol;
    }

    public static ConfirmarElIngreso de(DatosEscenario datos, CuentaPrueba cuenta) {
        return Tasks.instrumented(ConfirmarElIngreso.class, datos, cuenta);
    }
}
