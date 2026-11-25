package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import co.edu.udea.certificacion.prueba.questions.DatosEnPantalla;
import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

public class GuardarElServicioPrincipal implements Interaction {

    private final DatosEscenario datos;

    public GuardarElServicioPrincipal(DatosEscenario datos) {
        this.datos = datos;
    }

    @Override
    @Step("{0} anota el identificador de su servicio")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Esperar.visible(ReservaYa.fichaServicio(datos.proveedor.servicio)));
        datos.servicio = actor.asksFor(DatosEnPantalla.idServicio(datos.proveedor.servicio));
    }

    public static GuardarElServicioPrincipal en(DatosEscenario datos) {
        return Tasks.instrumented(GuardarElServicioPrincipal.class, datos);
    }
}
