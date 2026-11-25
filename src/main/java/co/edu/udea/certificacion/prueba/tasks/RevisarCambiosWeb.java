package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.DesplegarLosCambios;
import co.edu.udea.certificacion.prueba.interactions.RecargarElHistorialDeLaReserva;
import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class RevisarCambiosWeb implements Task {

    private final DatosEscenario datos;

    public RevisarCambiosWeb(DatosEscenario datos) {
        this.datos = datos;
    }

    @Override
    @Step("{0} revisa los cambios de su reserva cancelada")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                RecargarElHistorialDeLaReserva.de(datos),
                DesplegarLosCambios.deLaReserva(datos));
    }

    public static RevisarCambiosWeb de(DatosEscenario datos) {
        return Tasks.instrumented(RevisarCambiosWeb.class, datos);
    }
}
