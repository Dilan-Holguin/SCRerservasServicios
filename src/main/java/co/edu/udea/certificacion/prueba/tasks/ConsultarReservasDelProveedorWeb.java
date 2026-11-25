package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.AbrirElHistorialVacioDelProveedor;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class ConsultarReservasDelProveedorWeb implements Task {

    @Override
    @Step("{0} consulta las reservas de su negocio")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(AbrirElHistorialVacioDelProveedor.enElPanel());
    }

    public static ConsultarReservasDelProveedorWeb sinReservas() {
        return Tasks.instrumented(ConsultarReservasDelProveedorWeb.class);
    }
}
