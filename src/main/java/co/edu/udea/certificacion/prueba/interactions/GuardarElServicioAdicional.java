package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import co.edu.udea.certificacion.prueba.questions.DatosEnPantalla;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

public class GuardarElServicioAdicional implements Interaction {

    private final DatosEscenario datos;
    private final String nombre;

    public GuardarElServicioAdicional(DatosEscenario datos, String nombre) {
        this.datos = datos;
        this.nombre = nombre;
    }

    @Override
    @Step("{0} anota el identificador del segundo servicio")
    public <T extends Actor> void performAs(T actor) {
        datos.servicioSecundario = actor.asksFor(DatosEnPantalla.idServicio(nombre));
        datos.serviciosExtra.put(datos.servicioSecundario, nombre);
    }

    public static GuardarElServicioAdicional en(DatosEscenario datos, String nombre) {
        return Tasks.instrumented(GuardarElServicioAdicional.class, datos, nombre);
    }
}
