package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.RecargarElHistorialDeLaReserva;
import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class RecargarHistorialWeb implements Task {

    private final DatosEscenario datos;

    public RecargarHistorialWeb(DatosEscenario datos) {
        this.datos = datos;
    }

    @Override
    @Step("{0} vuelve a consultar el historial")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(RecargarElHistorialDeLaReserva.de(datos));
    }

    public static RecargarHistorialWeb de(DatosEscenario datos) {
        return Tasks.instrumented(RecargarHistorialWeb.class, datos);
    }
}
