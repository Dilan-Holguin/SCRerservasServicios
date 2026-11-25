package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.AbrirElHistorialDelCliente;
import co.edu.udea.certificacion.prueba.interactions.GuardarLaReservaMostrada;
import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class ConsultarReservaDelCliente implements Task {

    private final DatosEscenario datos;

    public ConsultarReservaDelCliente(DatosEscenario datos) {
        this.datos = datos;
    }

    @Override
    @Step("{0} busca su reserva en el historial")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                AbrirElHistorialDelCliente.mostrando(datos.proveedor.servicio),
                GuardarLaReservaMostrada.de(datos, datos.proveedor.servicio));
    }

    public static ConsultarReservaDelCliente de(DatosEscenario datos) {
        return Tasks.instrumented(ConsultarReservaDelCliente.class, datos);
    }
}
