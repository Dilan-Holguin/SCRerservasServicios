package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.GuardarElServicioAdicional;
import co.edu.udea.certificacion.prueba.interactions.PublicarElServicio;
import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class CrearServicioWeb implements Task {

    private final DatosEscenario datos;

    public CrearServicioWeb(DatosEscenario datos) {
        this.datos = datos;
    }

    @Override
    @Step("{0} publica un segundo servicio")
    public <T extends Actor> void performAs(T actor) {
        String nombre = datos.nombreServicioAdicional();
        actor.attemptsTo(
                PublicarElServicio.llamado(nombre),
                GuardarElServicioAdicional.en(datos, nombre));
    }

    public static CrearServicioWeb adicional(DatosEscenario datos) {
        return Tasks.instrumented(CrearServicioWeb.class, datos);
    }
}
