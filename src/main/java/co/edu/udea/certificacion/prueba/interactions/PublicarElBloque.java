package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import java.util.List;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

public class PublicarElBloque implements Interaction {

    private final List<Integer> dias;
    private final String desde;
    private final String hasta;

    public PublicarElBloque(List<Integer> dias, String desde, String hasta) {
        this.dias = dias;
        this.desde = desde;
        this.hasta = hasta;
    }

    @Override
    @Step("{0} publica el bloque en los días elegidos")
    public <T extends Actor> void performAs(T actor) {
        for (int dia : dias) {
            actor.attemptsTo(Click.on(ReservaYa.dia(dia)));
        }
        actor.attemptsTo(
                FijarHora.a(ReservaYa.HORA_INICIO, desde),
                FijarHora.a(ReservaYa.HORA_FIN, hasta),
                Click.on(ReservaYa.CREAR_BLOQUES));
        PausaDeDemostracion.hacer();
    }

    public static PublicarElBloque en(List<Integer> dias, String desde, String hasta) {
        return Tasks.instrumented(PublicarElBloque.class, dias, desde, hasta);
    }
}
