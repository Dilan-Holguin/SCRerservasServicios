package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.AbrirLaPaginaDeVerificacion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class AbrirVerificacionWeb implements Task {

    private final String token;

    public AbrirVerificacionWeb(String token) {
        this.token = token;
    }

    @Override
    @Step("{0} abre un enlace de verificación")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(AbrirLaPaginaDeVerificacion.conToken(token));
    }

    public static AbrirVerificacionWeb conToken(String token) {
        return Tasks.instrumented(AbrirVerificacionWeb.class, token);
    }
}
