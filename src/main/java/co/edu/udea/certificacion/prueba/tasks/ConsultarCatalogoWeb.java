package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.AbrirElServicioDelCatalogo;
import co.edu.udea.certificacion.prueba.interactions.ElegirElRangoDeFechas;
import co.edu.udea.certificacion.prueba.interactions.EsperarElTurno;
import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class ConsultarCatalogoWeb implements Task {

    private final DatosEscenario datos;

    public ConsultarCatalogoWeb(DatosEscenario datos) {
        this.datos = datos;
    }

    @Override
    @Step("{0} abre su servicio desde el catálogo")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                AbrirElServicioDelCatalogo.llamado(datos.proveedor.servicio),
                ElegirElRangoDeFechas.entre(datos.fecha.toString(), datos.fecha.toString()),
                EsperarElTurno.deLas("09:00"));
    }

    public static ConsultarCatalogoWeb de(DatosEscenario datos) {
        return Tasks.instrumented(ConsultarCatalogoWeb.class, datos);
    }
}
