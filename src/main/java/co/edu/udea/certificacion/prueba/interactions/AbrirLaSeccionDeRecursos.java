package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.FlujosUi;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;

public class AbrirLaSeccionDeRecursos implements Interaction {

    @Override
    @Step("{0} abre la sección de recursos")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.url(ConfiguracionUi.webUrl() + "/panel/proveedor?seccion=recursos"),
                Esperar.visible(FlujosUi.RECURSO_NOMBRE));
        PausaDeDemostracion.hacer();
    }

    public static AbrirLaSeccionDeRecursos delPanel() {
        return Tasks.instrumented(AbrirLaSeccionDeRecursos.class);
    }
}
