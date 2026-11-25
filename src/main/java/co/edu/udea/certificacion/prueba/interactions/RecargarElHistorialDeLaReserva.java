package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;

public class RecargarElHistorialDeLaReserva implements Interaction {

    private final DatosEscenario datos;

    public RecargarElHistorialDeLaReserva(DatosEscenario datos) {
        this.datos = datos;
    }

    @Override
    @Step("{0} carga de nuevo el historial de la reserva")
    public <T extends Actor> void performAs(T actor) {
        if (datos.reserva <= 0) {
            throw new IllegalStateException("Primero debe leerse el identificador de la reserva en el historial.");
        }
        String ruta = "PROVEEDOR".equals(datos.rolActual) ? "/panel/proveedor?seccion=reservas" : "/panel/cliente";
        actor.attemptsTo(
                Open.url(ConfiguracionUi.webUrl() + ruta),
                Esperar.visible(ReservaYa.tarjeta(datos.reserva)));
        PausaDeDemostracion.hacer();
    }

    public static RecargarElHistorialDeLaReserva de(DatosEscenario datos) {
        return Tasks.instrumented(RecargarElHistorialDeLaReserva.class, datos);
    }
}
