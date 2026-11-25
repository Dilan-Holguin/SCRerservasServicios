package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

public class CerrarSesionSiEstaAbierta implements Interaction {

    @Override
    @Step("{0} cierra la sesión abierta, si la hay")
    public <T extends Actor> void performAs(T actor) {
        if (ReservaYa.CERRAR_SESION.resolveFor(actor).isVisible()) {
            actor.attemptsTo(Click.on(ReservaYa.CERRAR_SESION), Esperar.noVisible(ReservaYa.CERRAR_SESION));
            PausaDeDemostracion.hacer();
        }
    }

    public static CerrarSesionSiEstaAbierta enElMenu() {
        return Tasks.instrumented(CerrarSesionSiEstaAbierta.class);
    }
}
