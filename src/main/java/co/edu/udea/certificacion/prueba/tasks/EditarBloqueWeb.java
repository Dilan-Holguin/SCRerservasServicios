package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.EditarElBloqueOriginal;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class EditarBloqueWeb implements Task {

    private final String desde;
    private final String hasta;

    public EditarBloqueWeb(String desde, String hasta) {
        this.desde = desde;
        this.hasta = hasta;
    }

    @Override
    @Step("{0} edita el bloque original")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(EditarElBloqueOriginal.a(desde, hasta));
    }

    public static EditarBloqueWeb a(String desde, String hasta) {
        return Tasks.instrumented(EditarBloqueWeb.class, desde, hasta);
    }
}
