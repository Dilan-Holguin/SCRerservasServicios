package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;

public class InactivarElServicio implements Interaction {

    private final String nombre;

    public InactivarElServicio(String nombre) {
        this.nombre = nombre;
    }

    @Override
    @Step("{0} inactiva el servicio si sigue activo")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.url(ConfiguracionUi.webUrl() + "/panel/proveedor?seccion=servicios"),
                Esperar.visible(ReservaYa.fichaServicio(nombre)));
        if (ReservaYa.inactivar(nombre).resolveFor(actor).isCurrentlyVisible()) {
            actor.attemptsTo(
                    Click.on(ReservaYa.inactivar(nombre)),
                    Esperar.clicable(ReservaYa.CONFIRMAR_INACTIVACION),
                    Click.on(ReservaYa.CONFIRMAR_INACTIVACION),
                    Esperar.visible(ReservaYa.servicioInactivo(nombre)));
        }
    }

    public static InactivarElServicio siSigueActivo(String nombre) {
        return Tasks.instrumented(InactivarElServicio.class, nombre);
    }
}
