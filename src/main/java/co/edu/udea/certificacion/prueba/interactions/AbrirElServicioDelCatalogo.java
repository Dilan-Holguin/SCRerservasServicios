package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.FlujosUi;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;

public class AbrirElServicioDelCatalogo implements Interaction {

    private final String nombre;

    public AbrirElServicioDelCatalogo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    @Step("{0} abre un servicio desde el catálogo")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.url(ConfiguracionUi.webUrl() + "/"),
                Esperar.clicable(FlujosUi.fichaCatalogo(nombre)),
                Click.on(FlujosUi.fichaCatalogo(nombre)),
                Esperar.visible(FlujosUi.DESDE));
        PausaDeDemostracion.hacer();
    }

    public static AbrirElServicioDelCatalogo llamado(String nombre) {
        return Tasks.instrumented(AbrirElServicioDelCatalogo.class, nombre);
    }
}
