package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.AbrirLaFichaDelServicio;
import co.edu.udea.certificacion.prueba.interactions.EsperarElResultadoDeLosTurnos;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class ConsultarTurnosWeb implements Task {

    private final int servicio;
    private final String desde;
    private final String hasta;

    public ConsultarTurnosWeb(int servicio, String desde, String hasta) {
        this.servicio = servicio;
        this.desde = desde;
        this.hasta = hasta;
    }

    @Override
    @Step("{0} consulta la disponibilidad de la ficha pública")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                AbrirLaFichaDelServicio.entre(servicio, desde, hasta),
                EsperarElResultadoDeLosTurnos.enLaFicha());
    }

    public static ConsultarTurnosWeb de(int servicio, String desde, String hasta) {
        return Tasks.instrumented(ConsultarTurnosWeb.class, servicio, desde, hasta);
    }
}
