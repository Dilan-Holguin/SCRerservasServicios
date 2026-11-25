package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

public class PulsarCancelarReserva implements Interaction {

    private final int reserva;

    public PulsarCancelarReserva(int reserva) {
        this.reserva = reserva;
    }

    @Override
    @Step("{0} pulsa cancelar en la reserva")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Esperar.clicable(ReservaYa.cancelar(reserva)), Click.on(ReservaYa.cancelar(reserva)));
        PausaDeDemostracion.hacer();
    }

    public static PulsarCancelarReserva numero(int reserva) {
        return Tasks.instrumented(PulsarCancelarReserva.class, reserva);
    }
}
