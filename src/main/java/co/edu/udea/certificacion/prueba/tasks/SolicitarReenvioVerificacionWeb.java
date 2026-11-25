package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.PedirElReenvio;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class SolicitarReenvioVerificacionWeb implements Task {

    private final String email;

    public SolicitarReenvioVerificacionWeb(String email) {
        this.email = email;
    }

    @Override
    @Step("{0} solicita el reenvío del correo")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(PedirElReenvio.alCorreo(email));
    }

    public static SolicitarReenvioVerificacionWeb para(String email) {
        return Tasks.instrumented(SolicitarReenvioVerificacionWeb.class, email);
    }
}
