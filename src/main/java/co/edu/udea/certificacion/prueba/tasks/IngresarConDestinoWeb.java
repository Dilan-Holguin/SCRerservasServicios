package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.AutenticarEnElFormulario;
import co.edu.udea.certificacion.prueba.models.CuentaPrueba;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

/** Conserva el destino y el turno que la navegación de la UI guardó antes del ingreso. */
public class IngresarConDestinoWeb implements Task {

    private final CuentaPrueba cuenta;

    public IngresarConDestinoWeb(CuentaPrueba cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    @Step("{0} ingresa sin perder el turno pendiente")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(AutenticarEnElFormulario.con(cuenta.email, cuenta.clave));
    }

    public static IngresarConDestinoWeb con(CuentaPrueba cuenta) {
        return Tasks.instrumented(IngresarConDestinoWeb.class, cuenta);
    }
}
