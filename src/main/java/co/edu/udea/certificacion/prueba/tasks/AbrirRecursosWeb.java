package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.AbrirLaSeccionDeRecursos;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class AbrirRecursosWeb implements Task {

    @Override
    @Step("{0} abre sus recursos")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(AbrirLaSeccionDeRecursos.delPanel());
    }

    public static AbrirRecursosWeb delPanel() {
        return Tasks.instrumented(AbrirRecursosWeb.class);
    }
}
