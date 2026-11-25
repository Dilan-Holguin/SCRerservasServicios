package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;

public class CancelarSiSigueConfirmada implements Interaction {

    private final int reserva;

    public CancelarSiSigueConfirmada(int reserva) {
        this.reserva = reserva;
    }

    @Override
    @Step("{0} cancela la reserva si sigue confirmada")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.url(ConfiguracionUi.webUrl() + "/panel/proveedor?seccion=reservas"),
                Esperar.visible(ReservaYa.tarjeta(reserva)));
        if (ReservaYa.estadoReserva(reserva, "CONFIRMADA").resolveFor(actor).isCurrentlyVisible()) {
            actor.attemptsTo(
                    PulsarCancelarReserva.numero(reserva),
                    ResponderLaCancelacion.confirmando(true),
                    Esperar.visible(ReservaYa.cancelacionConfirmada(reserva)));
        }
    }

    public static CancelarSiSigueConfirmada laReserva(int reserva) {
        return Tasks.instrumented(CancelarSiSigueConfirmada.class, reserva);
    }
}
