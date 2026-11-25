package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

public class ResponderLaCancelacion implements Interaction {

    private final boolean confirmar;

    public ResponderLaCancelacion(boolean confirmar) {
        this.confirmar = confirmar;
    }

    @Override
    @Step("{0} responde el diálogo de cancelación")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Esperar.clicable(ReservaYa.CONFIRMAR_CANCELACION),
                Click.on(confirmar ? ReservaYa.CONFIRMAR_CANCELACION : ReservaYa.CONSERVAR_RESERVA));
        PausaDeDemostracion.hacer();
    }

    public static ResponderLaCancelacion confirmando(boolean confirmar) {
        return Tasks.instrumented(ResponderLaCancelacion.class, confirmar);
    }
}
