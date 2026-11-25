package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.FlujosUi;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

public class ResponderElDialogoDeAgenda implements Interaction {

    private final String accion;

    public ResponderElDialogoDeAgenda(String accion) {
        this.accion = accion;
    }

    @Override
    @Step("{0} responde el diálogo de la agenda")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Esperar.clicable(FlujosUi.modal(accion)), Click.on(FlujosUi.modal(accion)));
        PausaDeDemostracion.hacer();
    }

    public static ResponderElDialogoDeAgenda con(String accion) {
        return Tasks.instrumented(ResponderElDialogoDeAgenda.class, accion);
    }
}
