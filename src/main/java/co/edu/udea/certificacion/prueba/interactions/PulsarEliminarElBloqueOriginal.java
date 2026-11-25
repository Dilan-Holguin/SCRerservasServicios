package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.FlujosUi;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

public class PulsarEliminarElBloqueOriginal implements Interaction {

    @Override
    @Step("{0} pulsa eliminar en el bloque original")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Esperar.clicable(FlujosUi.ELIMINAR_BLOQUE), Click.on(FlujosUi.ELIMINAR_BLOQUE));
        PausaDeDemostracion.hacer();
    }

    public static PulsarEliminarElBloqueOriginal enLaAgenda() {
        return Tasks.instrumented(PulsarEliminarElBloqueOriginal.class);
    }
}
