package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.AbrirElPanelProtegido;
import co.edu.udea.certificacion.prueba.interactions.CerrarSesionSiEstaAbierta;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class IntentarAbrirPanelWeb implements Task {

    @Override
    @Step("{0} intenta volver al panel sin sesión")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                CerrarSesionSiEstaAbierta.enElMenu(),
                AbrirElPanelProtegido.delCliente());
    }

    public static IntentarAbrirPanelWeb delCliente() {
        return Tasks.instrumented(IntentarAbrirPanelWeb.class);
    }
}
