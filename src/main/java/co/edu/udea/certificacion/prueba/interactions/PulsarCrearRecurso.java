package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.FlujosUi;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

public class PulsarCrearRecurso implements Interaction {

    @Override
    @Step("{0} guarda el recurso")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Click.on(FlujosUi.boton("Crear recurso")));
        PausaDeDemostracion.hacer();
    }

    public static PulsarCrearRecurso enElFormulario() {
        return Tasks.instrumented(PulsarCrearRecurso.class);
    }
}
