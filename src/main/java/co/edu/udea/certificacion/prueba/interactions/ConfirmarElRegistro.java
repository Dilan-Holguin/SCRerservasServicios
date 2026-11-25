package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.models.CuentaPrueba;
import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

public class ConfirmarElRegistro implements Interaction {

    private final CuentaPrueba cuenta;

    public ConfirmarElRegistro(CuentaPrueba cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    @Step("{0} espera la confirmación del registro")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Esperar.visible(ReservaYa.CUENTA_CREADA));
        cuenta.registrada = true;
    }

    public static ConfirmarElRegistro de(CuentaPrueba cuenta) {
        return Tasks.instrumented(ConfirmarElRegistro.class, cuenta);
    }
}
