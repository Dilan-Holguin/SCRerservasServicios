package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import co.edu.udea.certificacion.prueba.userinterfaces.FlujosUi;
import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.SelectFromOptions;

public class FiltrarLasReservasCanceladas implements Interaction {

    private final DatosEscenario datos;

    public FiltrarLasReservasCanceladas(DatosEscenario datos) {
        this.datos = datos;
    }

    @Override
    @Step("{0} filtra las reservas canceladas")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                SelectFromOptions.byValue("CANCELADA").from(FlujosUi.FILTRO_ESTADO),
                Esperar.visible(ReservaYa.estadoReserva(datos.reserva, "CANCELADA")));
        PausaDeDemostracion.hacer();
    }

    public static FiltrarLasReservasCanceladas de(DatosEscenario datos) {
        return Tasks.instrumented(FiltrarLasReservasCanceladas.class, datos);
    }
}
