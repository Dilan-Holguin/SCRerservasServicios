package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import co.edu.udea.certificacion.prueba.questions.DatosEnPantalla;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

public class GuardarLaReservaMostrada implements Interaction {

    private final DatosEscenario datos;
    private final String nombreServicio;

    public GuardarLaReservaMostrada(DatosEscenario datos, String nombreServicio) {
        this.datos = datos;
        this.nombreServicio = nombreServicio;
    }

    @Override
    @Step("{0} anota el identificador de la reserva")
    public <T extends Actor> void performAs(T actor) {
        datos.reserva = actor.asksFor(DatosEnPantalla.idReserva(nombreServicio));
    }

    public static GuardarLaReservaMostrada de(DatosEscenario datos, String nombreServicio) {
        return Tasks.instrumented(GuardarLaReservaMostrada.class, datos, nombreServicio);
    }
}
