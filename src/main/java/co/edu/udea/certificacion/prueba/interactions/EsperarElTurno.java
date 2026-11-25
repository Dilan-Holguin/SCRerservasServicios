package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

public class EsperarElTurno implements Interaction {

    private final String hora;

    public EsperarElTurno(String hora) {
        this.hora = hora;
    }

    @Override
    @Step("{0} espera ver el turno")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Esperar.visible(ReservaYa.turno(hora)));
    }

    public static EsperarElTurno deLas(String hora) {
        return Tasks.instrumented(EsperarElTurno.class, hora);
    }
}
