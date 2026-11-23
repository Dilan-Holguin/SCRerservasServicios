package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import co.edu.udea.certificacion.prueba.userinterfaces.FlujosUi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public final class PrepararRecursosCompartidosWeb {
    private PrepararRecursosCompartidosWeb() { }
    public static Performable para(DatosEscenario datos) {
        return Task.where("{0} prepara dos servicios que comparten un recurso", actor -> {
            actor.attemptsTo(PrepararNegocioWeb.para(datos, 10, true, false));
            actor.attemptsTo(CrearServicioWeb.adicional(datos));
            // La fecha y el segundo identificador se leen después de crearlos.
            actor.attemptsTo(CrearAgendaWeb.para(datos.servicioSecundario, datos.fecha.getDayOfWeek().getValue(), "08:00", "12:00"),
                    WaitUntil.the(co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa.BLOQUE_CREADO, isVisible()).forNoMoreThan(15).seconds());
            datos.nombreRecurso = "Recurso " + java.util.UUID.randomUUID();
            actor.attemptsTo(GestionarRecursosWeb.crear(datos.nombreRecurso, true),
                    WaitUntil.the(FlujosUi.recurso(datos.nombreRecurso), isVisible()).forNoMoreThan(15).seconds(),
                    GestionarRecursosWeb.asignar(datos.servicio, datos.nombreRecurso, true),
                    GestionarRecursosWeb.asignar(datos.servicioSecundario, datos.nombreRecurso, true),
                    FlujoReservaWeb.crearCliente(datos, false),
                    FlujoReservaWeb.reservar(datos, datos.servicio, datos.proveedor.servicio),
                    FlujoReservaWeb.crearCliente(datos, true));
        });
    }
}
