package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.FlujosUi;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

public class EditarElBloqueOriginal implements Interaction {

    private final String desde;
    private final String hasta;

    public EditarElBloqueOriginal(String desde, String hasta) {
        this.desde = desde;
        this.hasta = hasta;
    }

    @Override
    @Step("{0} cambia el horario del bloque original")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Esperar.clicable(FlujosUi.EDITAR_BLOQUE),
                Click.on(FlujosUi.EDITAR_BLOQUE),
                FijarHora.a(FlujosUi.EDICION_INICIO, desde),
                FijarHora.a(FlujosUi.EDICION_FIN, hasta),
                Click.on(FlujosUi.boton("Guardar cambios")));
        PausaDeDemostracion.hacer();
    }

    public static EditarElBloqueOriginal a(String desde, String hasta) {
        return Tasks.instrumented(EditarElBloqueOriginal.class, desde, hasta);
    }
}
