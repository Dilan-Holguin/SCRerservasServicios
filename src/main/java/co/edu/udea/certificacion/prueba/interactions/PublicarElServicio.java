package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.FlujosUi;
import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actions.SelectFromOptions;

public class PublicarElServicio implements Interaction {

    private final String nombre;

    public PublicarElServicio(String nombre) {
        this.nombre = nombre;
    }

    @Override
    @Step("{0} publica un servicio desde su panel")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.url(ConfiguracionUi.webUrl() + "/panel/proveedor?seccion=servicios"),
                Esperar.visible(FlujosUi.SERVICIO_NOMBRE),
                Enter.theValue(nombre).into(FlujosUi.SERVICIO_NOMBRE),
                Esperar.presente(FlujosUi.CATEGORIA_CARGADA),
                SelectFromOptions.byIndex(1).from(FlujosUi.SERVICIO_CATEGORIA),
                Click.on(FlujosUi.boton("Crear servicio")),
                Esperar.visible(ReservaYa.fichaServicio(nombre)));
        PausaDeDemostracion.hacer();
    }

    public static PublicarElServicio llamado(String nombre) {
        return Tasks.instrumented(PublicarElServicio.class, nombre);
    }
}
