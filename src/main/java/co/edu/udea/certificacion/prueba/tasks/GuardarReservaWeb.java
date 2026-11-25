package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.AbrirElHistorialDelCliente;
import co.edu.udea.certificacion.prueba.interactions.GuardarLaReservaMostrada;
import co.edu.udea.certificacion.prueba.interactions.RegistrarLaReservaCreada;
import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class GuardarReservaWeb implements Task {

    private final DatosEscenario datos;
    private final String nombreServicio;

    public GuardarReservaWeb(DatosEscenario datos, String nombreServicio) {
        this.datos = datos;
        this.nombreServicio = nombreServicio;
    }

    @Override
    @Step("{0} guarda el identificador de su reserva")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                AbrirElHistorialDelCliente.mostrando(nombreServicio),
                GuardarLaReservaMostrada.de(datos, nombreServicio),
                RegistrarLaReservaCreada.en(datos));
    }

    public static GuardarReservaWeb de(DatosEscenario datos, String nombreServicio) {
        return Tasks.instrumented(GuardarReservaWeb.class, datos, nombreServicio);
    }
}
