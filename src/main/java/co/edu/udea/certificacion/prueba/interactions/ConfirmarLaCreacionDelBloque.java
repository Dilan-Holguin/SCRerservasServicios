package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

public class ConfirmarLaCreacionDelBloque implements Interaction {

    @Override
    @Step("{0} espera el aviso de bloque creado")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Esperar.visible(ReservaYa.BLOQUE_CREADO));
    }

    public static ConfirmarLaCreacionDelBloque enPantalla() {
        return Tasks.instrumented(ConfirmarLaCreacionDelBloque.class);
    }
}
