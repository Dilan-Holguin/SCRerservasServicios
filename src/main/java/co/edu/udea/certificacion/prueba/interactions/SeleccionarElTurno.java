package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

public class SeleccionarElTurno implements Interaction {

    private final String hora;

    public SeleccionarElTurno(String hora) {
        this.hora = hora;
    }

    @Override
    @Step("{0} selecciona el turno")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Esperar.clicable(ReservaYa.turno(hora)), Click.on(ReservaYa.turno(hora)));
        PausaDeDemostracion.hacer();
    }

    public static SeleccionarElTurno deLas(String hora) {
        return Tasks.instrumented(SeleccionarElTurno.class, hora);
    }
}
