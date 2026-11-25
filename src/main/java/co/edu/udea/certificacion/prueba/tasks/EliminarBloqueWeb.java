package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.PulsarEliminarElBloqueOriginal;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class EliminarBloqueWeb implements Task {

    @Override
    @Step("{0} solicita eliminar el bloque original")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(PulsarEliminarElBloqueOriginal.enLaAgenda());
    }

    public static EliminarBloqueWeb original() {
        return Tasks.instrumented(EliminarBloqueWeb.class);
    }
}
