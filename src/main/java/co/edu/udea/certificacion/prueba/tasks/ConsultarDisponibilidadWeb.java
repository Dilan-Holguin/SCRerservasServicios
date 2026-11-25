package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.AbrirLaFichaDelServicio;
import co.edu.udea.certificacion.prueba.interactions.EsperarElTurno;
import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class ConsultarDisponibilidadWeb implements Task {

    private final DatosEscenario datos;

    public ConsultarDisponibilidadWeb(DatosEscenario datos) {
        this.datos = datos;
    }

    @Override
    @Step("{0} consulta los turnos del día preparado")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                AbrirLaFichaDelServicio.entre(datos.servicio, datos.fecha.toString(), datos.fecha.toString()),
                EsperarElTurno.deLas("09:00"));
    }

    public static ConsultarDisponibilidadWeb de(DatosEscenario datos) {
        return Tasks.instrumented(ConsultarDisponibilidadWeb.class, datos);
    }
}
