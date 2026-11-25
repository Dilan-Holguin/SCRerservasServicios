package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import co.edu.udea.certificacion.prueba.userinterfaces.FlujosUi;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

public class DesplegarLosCambios implements Interaction {

    private final DatosEscenario datos;

    public DesplegarLosCambios(DatosEscenario datos) {
        this.datos = datos;
    }

    @Override
    @Step("{0} despliega los cambios de la reserva")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Click.on(FlujosUi.historial(datos.reserva)));
        PausaDeDemostracion.hacer();
    }

    public static DesplegarLosCambios deLaReserva(DatosEscenario datos) {
        return Tasks.instrumented(DesplegarLosCambios.class, datos);
    }
}
