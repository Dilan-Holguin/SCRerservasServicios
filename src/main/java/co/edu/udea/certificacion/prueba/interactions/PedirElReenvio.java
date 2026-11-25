package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;

public class PedirElReenvio implements Interaction {

    private final String email;

    public PedirElReenvio(String email) {
        this.email = email;
    }

    @Override
    @Step("{0} pide el reenvío del correo de verificación")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.url(ConfiguracionUi.webUrl() + "/verificacion"),
                Esperar.visible(ReservaYa.EMAIL),
                Enter.theValue(email).into(ReservaYa.EMAIL),
                Click.on(ReservaYa.REENVIAR_VERIFICACION),
                Esperar.visible(ReservaYa.RESPUESTA_REENVIO));
        PausaDeDemostracion.hacer();
    }

    public static PedirElReenvio alCorreo(String email) {
        return Tasks.instrumented(PedirElReenvio.class, email);
    }
}
