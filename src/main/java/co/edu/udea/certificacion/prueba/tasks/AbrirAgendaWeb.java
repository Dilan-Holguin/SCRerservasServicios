package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.AbrirLaAgendaDelServicio;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class AbrirAgendaWeb implements Task {

    private final int servicio;

    public AbrirAgendaWeb(int servicio) {
        this.servicio = servicio;
    }

    @Override
    @Step("{0} abre la agenda de su servicio")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(AbrirLaAgendaDelServicio.numero(servicio));
    }

    public static AbrirAgendaWeb delServicio(int servicio) {
        return Tasks.instrumented(AbrirAgendaWeb.class, servicio);
    }
}
