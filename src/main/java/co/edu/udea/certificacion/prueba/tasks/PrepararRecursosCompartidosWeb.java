package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.ConfirmarElRecurso;
import co.edu.udea.certificacion.prueba.interactions.ConfirmarLaCreacionDelBloque;
import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class PrepararRecursosCompartidosWeb implements Task {

    private final DatosEscenario datos;

    public PrepararRecursosCompartidosWeb(DatosEscenario datos) {
        this.datos = datos;
    }

    @Override
    @Step("{0} prepara dos servicios que comparten un recurso")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                PrepararNegocioWeb.para(datos, 10, true, false),
                CrearServicioWeb.adicional(datos));
        // Se llama aparte: el segundo identificador se conoce después de crear el servicio.
        String recurso = datos.nuevoRecurso();
        actor.attemptsTo(
                CrearAgendaWeb.para(datos.servicioSecundario, datos.diaDeLaAgenda(), "08:00", "12:00"),
                ConfirmarLaCreacionDelBloque.enPantalla(),
                CrearRecursoWeb.con(recurso, true),
                ConfirmarElRecurso.llamado(recurso),
                AsignarRecursoWeb.al(datos.servicio, recurso, true),
                AsignarRecursoWeb.al(datos.servicioSecundario, recurso, true),
                CrearClienteWeb.principal(datos),
                ReservarYGuardarWeb.en(datos, datos.servicio, datos.proveedor.servicio),
                CrearClienteWeb.adicional(datos));
    }

    public static PrepararRecursosCompartidosWeb para(DatosEscenario datos) {
        return Tasks.instrumented(PrepararRecursosCompartidosWeb.class, datos);
    }
}
