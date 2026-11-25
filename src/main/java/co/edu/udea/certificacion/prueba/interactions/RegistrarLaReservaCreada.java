package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

public class RegistrarLaReservaCreada implements Interaction {

    private final DatosEscenario datos;

    public RegistrarLaReservaCreada(DatosEscenario datos) {
        this.datos = datos;
    }

    @Override
    @Step("{0} comprueba que la reserva quedó confirmada")
    public <T extends Actor> void performAs(T actor) {
        datos.reservasCreadas.add(datos.reserva);
        actor.attemptsTo(Esperar.visible(ReservaYa.estadoReserva(datos.reserva, "CONFIRMADA")));
    }

    public static RegistrarLaReservaCreada en(DatosEscenario datos) {
        return Tasks.instrumented(RegistrarLaReservaCreada.class, datos);
    }
}
