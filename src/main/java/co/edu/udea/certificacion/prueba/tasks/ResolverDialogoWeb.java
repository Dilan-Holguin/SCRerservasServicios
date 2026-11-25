package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.ResponderElDialogoDeAgenda;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class ResolverDialogoWeb implements Task {

    private final String accion;

    public ResolverDialogoWeb(String accion) {
        this.accion = accion;
    }

    @Override
    @Step("{0} decide sobre las reservas afectadas")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(ResponderElDialogoDeAgenda.con(accion));
    }

    public static ResolverDialogoWeb con(String accion) {
        return Tasks.instrumented(ResolverDialogoWeb.class, accion);
    }
}
