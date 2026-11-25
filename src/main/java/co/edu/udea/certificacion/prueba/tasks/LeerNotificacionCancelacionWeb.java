package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.BuscarElAvisoDeCancelacion;
import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

/** Contrato de producto pendiente; se activa expresamente con @pendiente_producto. */
public class LeerNotificacionCancelacionWeb implements Task {

    private final DatosEscenario datos;

    public LeerNotificacionCancelacionWeb(DatosEscenario datos) {
        this.datos = datos;
    }

    @Override
    @Step("{0} consulta el aviso de cancelación")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(BuscarElAvisoDeCancelacion.de(datos));
    }

    public static LeerNotificacionCancelacionWeb de(DatosEscenario datos) {
        return Tasks.instrumented(LeerNotificacionCancelacionWeb.class, datos);
    }
}
