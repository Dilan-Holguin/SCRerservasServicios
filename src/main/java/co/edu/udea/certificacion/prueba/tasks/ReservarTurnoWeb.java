package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.AbrirLaFichaDelServicio;
import co.edu.udea.certificacion.prueba.interactions.SeleccionarElTurno;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class ReservarTurnoWeb implements Task {

    private final int servicio;
    private final String fecha;
    private final String hora;

    public ReservarTurnoWeb(int servicio, String fecha, String hora) {
        this.servicio = servicio;
        this.fecha = fecha;
        this.hora = hora;
    }

    @Override
    @Step("{0} reserva un turno del servicio")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                AbrirLaFichaDelServicio.entre(servicio, fecha, fecha),
                SeleccionarElTurno.deLas(hora));
    }

    public static ReservarTurnoWeb para(int servicio, String fecha, String hora) {
        return Tasks.instrumented(ReservarTurnoWeb.class, servicio, fecha, hora);
    }
}
