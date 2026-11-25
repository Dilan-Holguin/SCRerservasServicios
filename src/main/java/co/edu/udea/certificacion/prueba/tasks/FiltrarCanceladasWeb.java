package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.DesplegarLosCambios;
import co.edu.udea.certificacion.prueba.interactions.FiltrarLasReservasCanceladas;
import co.edu.udea.certificacion.prueba.interactions.RecargarElHistorialDeLaReserva;
import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class FiltrarCanceladasWeb implements Task {

    private final DatosEscenario datos;

    public FiltrarCanceladasWeb(DatosEscenario datos) {
        this.datos = datos;
    }

    @Override
    @Step("{0} filtra sus reservas canceladas y revisa los cambios")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                RecargarElHistorialDeLaReserva.de(datos),
                FiltrarLasReservasCanceladas.de(datos),
                DesplegarLosCambios.deLaReserva(datos));
    }

    public static FiltrarCanceladasWeb de(DatosEscenario datos) {
        return Tasks.instrumented(FiltrarCanceladasWeb.class, datos);
    }
}
