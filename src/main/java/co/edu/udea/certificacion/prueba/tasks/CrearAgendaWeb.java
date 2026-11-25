package co.edu.udea.certificacion.prueba.tasks;

import net.serenitybdd.screenplay.Tasks;
import co.edu.udea.certificacion.prueba.interactions.AbrirLaAgendaDelServicio;
import co.edu.udea.certificacion.prueba.interactions.PublicarElBloque;
import java.util.List;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

public class CrearAgendaWeb implements Task {

    private final int servicio;
    private final List<Integer> dias;
    private final String desde;
    private final String hasta;

    public CrearAgendaWeb(int servicio, List<Integer> dias, String desde, String hasta) {
        this.servicio = servicio;
        this.dias = dias;
        this.desde = desde;
        this.hasta = hasta;
    }

    @Override
    @Step("{0} publica su agenda semanal")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                AbrirLaAgendaDelServicio.numero(servicio),
                PublicarElBloque.en(dias, desde, hasta));
    }

    public static CrearAgendaWeb para(int servicio, int dia, String desde, String hasta) {
        return paraDias(servicio, List.of(dia), desde, hasta);
    }

    public static CrearAgendaWeb paraDias(int servicio, List<Integer> dias, String desde, String hasta) {
        return Tasks.instrumented(CrearAgendaWeb.class, servicio, dias, desde, hasta);
    }
}
