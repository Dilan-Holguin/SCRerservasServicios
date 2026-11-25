package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.CerrarSesionSiEstaAbierta;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class CerrarSesionWeb implements Task {

    @Override
    @Step("{0} cierra su sesión")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(CerrarSesionSiEstaAbierta.enElMenu());
    }

    public static CerrarSesionWeb siExiste() {
        return Tasks.instrumented(CerrarSesionWeb.class);
    }
}
