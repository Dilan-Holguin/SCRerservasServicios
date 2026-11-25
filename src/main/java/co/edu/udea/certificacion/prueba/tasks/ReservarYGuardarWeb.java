package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.ConfirmarLaReserva;
import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class ReservarYGuardarWeb implements Task {

    private final DatosEscenario datos;
    private final int servicio;
    private final String nombreServicio;

    public ReservarYGuardarWeb(DatosEscenario datos, int servicio, String nombreServicio) {
        this.datos = datos;
        this.servicio = servicio;
        this.nombreServicio = nombreServicio;
    }

    @Override
    @Step("{0} reserva el turno de las nueve y lo guarda")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ReservarTurnoWeb.para(servicio, datos.fecha.toString(), "09:00"),
                ConfirmarLaReserva.enPantalla(),
                GuardarReservaWeb.de(datos, nombreServicio));
    }

    public static ReservarYGuardarWeb en(DatosEscenario datos, int servicio, String nombreServicio) {
        return Tasks.instrumented(ReservarYGuardarWeb.class, datos, servicio, nombreServicio);
    }
}
