package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class PrepararReservaWeb implements Task {

    private final DatosEscenario datos;
    private final String rol;

    public PrepararReservaWeb(DatosEscenario datos, String rol) {
        this.datos = datos;
        this.rol = rol;
    }

    @Override
    @Step("{0} prepara una reserva por la interfaz")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(PrepararNegocioWeb.para(datos, 10, true, true));
        // Se llama aparte: el servicio solo existe después de preparar el negocio.
        actor.attemptsTo(ReservarYGuardarWeb.en(datos, datos.servicio, datos.proveedor.servicio));
        if (rol.equals("PROVEEDOR")) {
            actor.attemptsTo(
                    AccederConCuentaWeb.de(datos, datos.proveedor),
                    RecargarHistorialWeb.de(datos));
        }
    }

    public static PrepararReservaWeb para(DatosEscenario datos, String rol) {
        return Tasks.instrumented(PrepararReservaWeb.class, datos, rol);
    }
}
