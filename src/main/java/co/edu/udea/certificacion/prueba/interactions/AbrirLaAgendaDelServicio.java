package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actions.SelectFromOptions;

public class AbrirLaAgendaDelServicio implements Interaction {

    private final int servicio;

    public AbrirLaAgendaDelServicio(int servicio) {
        this.servicio = servicio;
    }

    @Override
    @Step("{0} abre la agenda de su servicio")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.url(ConfiguracionUi.webUrl() + "/panel/proveedor?seccion=agenda"),
                Esperar.visible(ReservaYa.SELECTOR_SERVICIO),
                SelectFromOptions.byValue(String.valueOf(servicio)).from(ReservaYa.SELECTOR_SERVICIO));
        PausaDeDemostracion.hacer();
    }

    public static AbrirLaAgendaDelServicio numero(int servicio) {
        return Tasks.instrumented(AbrirLaAgendaDelServicio.class, servicio);
    }
}
