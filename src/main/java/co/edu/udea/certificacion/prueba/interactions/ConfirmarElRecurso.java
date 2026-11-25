package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.FlujosUi;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

public class ConfirmarElRecurso implements Interaction {

    private final String nombre;

    public ConfirmarElRecurso(String nombre) {
        this.nombre = nombre;
    }

    @Override
    @Step("{0} espera ver el recurso en el listado")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Esperar.visible(FlujosUi.recurso(nombre)));
    }

    public static ConfirmarElRecurso llamado(String nombre) {
        return Tasks.instrumented(ConfirmarElRecurso.class, nombre);
    }
}
