package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

public class PulsarCrearCuenta implements Interaction {

    @Override
    @Step("{0} envía el formulario de registro")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Click.on(ReservaYa.CREAR_CUENTA));
        PausaDeDemostracion.hacer();
    }

    public static PulsarCrearCuenta enElFormulario() {
        return Tasks.instrumented(PulsarCrearCuenta.class);
    }
}
