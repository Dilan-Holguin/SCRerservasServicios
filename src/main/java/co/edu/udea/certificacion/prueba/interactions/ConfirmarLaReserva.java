package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

public class ConfirmarLaReserva implements Interaction {

    @Override
    @Step("{0} espera el aviso de reserva confirmada")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Esperar.visible(ReservaYa.RESERVA_CONFIRMADA));
    }

    public static ConfirmarLaReserva enPantalla() {
        return Tasks.instrumented(ConfirmarLaReserva.class);
    }
}
