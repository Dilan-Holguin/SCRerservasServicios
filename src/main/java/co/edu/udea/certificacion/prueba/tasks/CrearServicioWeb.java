package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import co.edu.udea.certificacion.prueba.questions.DatosEnPantalla;
import co.edu.udea.certificacion.prueba.userinterfaces.*;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;

public final class CrearServicioWeb {
    private CrearServicioWeb() { }
    public static Performable adicional(DatosEscenario datos) {
        return Task.where("{0} publica un segundo servicio independiente", actor -> {
            String nombre = datos.proveedor.servicio + " B";
            actor.attemptsTo(Open.url(ConfiguracionUi.webUrl() + "/panel/proveedor?seccion=servicios"),
                    WaitUntil.the(FlujosUi.SERVICIO_NOMBRE, isVisible()).forNoMoreThan(15).seconds(),
                    Enter.theValue(nombre).into(FlujosUi.SERVICIO_NOMBRE),
                    WaitUntil.the(FlujosUi.CATEGORIA_CARGADA, isPresent()).forNoMoreThan(15).seconds(),
                    SelectFromOptions.byIndex(1).from(FlujosUi.SERVICIO_CATEGORIA),
                    Click.on(FlujosUi.boton("Crear servicio")),
                    WaitUntil.the(ReservaYa.fichaServicio(nombre), isVisible()).forNoMoreThan(15).seconds());
            datos.servicioSecundario = actor.asksFor(DatosEnPantalla.idServicio(nombre));
            datos.serviciosExtra.put(datos.servicioSecundario, nombre);
        });
    }
}
