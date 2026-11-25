package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.PulsarCancelarReserva;
import co.edu.udea.certificacion.prueba.interactions.ResponderLaCancelacion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class CancelarReservaWeb implements Task {

    private final int reserva;
    private final boolean confirmar;

    public CancelarReservaWeb(int reserva, boolean confirmar) {
        this.reserva = reserva;
        this.confirmar = confirmar;
    }

    @Override
    @Step("{0} solicita cancelar su reserva")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                PulsarCancelarReserva.numero(reserva),
                ResponderLaCancelacion.confirmando(confirmar));
    }

    public static CancelarReservaWeb conConfirmacion(int reserva, boolean confirmar) {
        return Tasks.instrumented(CancelarReservaWeb.class, reserva, confirmar);
    }
}
