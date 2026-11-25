package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

/** Pulsa sin exigir que se pueda reservar: sirve para comprobar el rechazo a un proveedor. */
public class PulsarElTurno implements Interaction {

    private final String hora;

    public PulsarElTurno(String hora) {
        this.hora = hora;
    }

    @Override
    @Step("{0} pulsa el turno")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Click.on(ReservaYa.turno(hora)));
        PausaDeDemostracion.hacer();
    }

    public static PulsarElTurno deLas(String hora) {
        return Tasks.instrumented(PulsarElTurno.class, hora);
    }
}
